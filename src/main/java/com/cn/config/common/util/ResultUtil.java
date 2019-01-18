package com.cn.config.common.util;

/**
 * @author NothingNull
 * @email NothingNull@foxmail.com
 * @date 2018/06/26 16:39
 */
public class ResultUtil<T> {

    /**
     * 返回成功，传入返回体具体出參
     * 
     * @param t
     * @return
     */
    public static <T> ResultDTO<T> success(T t) {
        ResultDTO<T> result = new ResultDTO<>();
        result.setSuccessd(true);
        result.setData(t);
        return result;
    }

    public static <T> ResultDTO<T> success(T t, String dataVersion) {
        ResultDTO<T> result = success(t);
        result.setDataVersion(dataVersion);
        return result;
    }

    /**
     * 提供给部分不需要出參的接口
     * 
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static ResultDTO success() {
        return success(null);
    }

    public static <T> ResultDTO<T> error(String errorMsg) {
        ResultDTO<T> result = new ResultDTO<>();
        result.setSuccessd(false);
        result.setError(errorMsg);
        result.setData(null);
        return result;
    }

}
