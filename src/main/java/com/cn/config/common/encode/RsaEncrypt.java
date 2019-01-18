/**
 * 
 */
package com.cn.config.common.encode;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.xml.bind.ValidationException;

import lombok.Data;

/**
 * RSA加密
 * 
 * @author Administrator
 */
@Data
public class RsaEncrypt {

    // 加密算法
    private static final String DES_TYPE = "MD5withRSA";

    /**
     * 私钥
     */
    private RSAPrivateKey       privateKey;

    /**
     * 公钥
     */
    private RSAPublicKey        publicKey;

    /**
     * rsa签名
     * 
     * @param data 待签名的字符串
     * @param rsaPrivateKey rsa私匙字符串
     * @return 签名结构
     * @throws SignatureException
     */
    public byte[] rsaSign(byte[] data, RSAPrivateKey rsaPrivateKey) throws SignatureException {
        try {
            Signature signature = Signature.getInstance(DES_TYPE);
            signature.initSign(rsaPrivateKey);
            signature.update(data);
            byte[] signed = signature.sign();
            return signed;
        } catch (Exception e) {
            throw new SignatureException("RSAcontent = " + data + "; charset = ", e);
        }
    }

    /**
     * 验证签名
     * 
     * @param data 被签名的内容
     * @param sign 签名后的结果
     * @param pubKey rsa公匙
     * @return 验证结果
     * @throws SignatureException
     */
    public boolean verify(byte[] data, byte[] sign, RSAPublicKey pubKey) throws SignatureException {
        try {
            Signature signature = Signature.getInstance(DES_TYPE);
            signature.initVerify(pubKey);
            signature.update(data);
            return signature.verify(sign);
        } catch (Exception e) {
            throw new SignatureException("RSA验证签名[content = " + data + "; charset = " + "; signature = " + sign
                                         + "]发生异常!", e);
        }
    }

    /**
     * 随机生成密钥对
     */
    public void genKeyPair() {
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyPairGen.initialize(1024, new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();
        this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
        this.publicKey = (RSAPublicKey) keyPair.getPublic();
    }

    /**
     * 从字符串中加载公钥
     * 
     * @param publicKeyStr 公钥数据字符串
     * @throws Exception 加载公钥时产生的异常
     */
    public void loadPublicKey(String publicKeyStr) throws Exception {
        try {
            byte[] buffer = Base64Utils.decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            this.publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }

    /**
     * 加载私钥
     * 
     * @param privateKeyStr 私钥文件名
     * @return 是否成功
     * @throws Exception
     */
    public void loadPrivateKey(String privateKeyStr) throws Exception {
        try {
            byte[] buffer = Base64Utils.decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            this.privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }

    /**
     * 加密过程
     * 
     * @param publicKey 公钥
     * @param plainTextData 明文数据
     * @return
     * @throws Exception 加密过程中的异常信息
     */
    public byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception {
        if (publicKey == null) {
            throw new ValidationException("加密公钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(plainTextData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (InvalidKeyException e) {
            throw new Exception("加密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }

    /**
     * 解密过程
     * 
     * @param privateKey 私钥
     * @param cipherData 密文数据
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception {
        if (privateKey == null) {
            throw new Exception("解密私钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(cipherData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (InvalidKeyException e) {
            throw new Exception("解密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }
    }

    public static void main(String[] args) throws Exception {
        RsaEncrypt rsaEncryptExt = new RsaEncrypt();
        rsaEncryptExt.genKeyPair();
        String publicKey = Base64Utils.encode(rsaEncryptExt.getPublicKey().getEncoded());
        String privateKey = Base64Utils.encode(rsaEncryptExt.getPrivateKey().getEncoded());
        RsaEncrypt rsaEncrypt = new RsaEncrypt();
        rsaEncrypt.loadPublicKey(publicKey);
        rsaEncrypt.loadPrivateKey(privateKey);
        String encryptStr = "wqeqdwdz";
        // 加密
        try {
            byte[] cipher = rsaEncrypt.encrypt(rsaEncrypt.getPublicKey(), encryptStr.getBytes());
            // 解密
            byte[] plainText = rsaEncrypt.decrypt(rsaEncrypt.getPrivateKey(), cipher);
            System.out.println(new String(plainText));
            // 获取签名
            byte[] signbyte = rsaEncrypt.rsaSign(encryptStr.getBytes(), rsaEncrypt.getPrivateKey());
            System.out.println(new String(signbyte));
            // 验证签名
            Boolean isok = rsaEncrypt.verify(encryptStr.getBytes(), signbyte, rsaEncrypt.getPublicKey());
            System.out.println("验证：" + isok);
        } catch (Exception e) {
        }
    }
}
