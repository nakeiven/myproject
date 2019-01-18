/**
 * 
 */
package com.cn.config.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author Administrator
 */
@Component
public class JedisUtil {

    protected final static Logger logger = Logger.getLogger(JedisUtil.class);

    private static JedisPool      jedisPool;

    @Autowired(required = true)
    public void setJedisPool(JedisPool jedisPool) {
        JedisUtil.jedisPool = jedisPool;
    }

    /**
     * 对某个键的值自增
     * 
     * @author liboyi
     * @param key 键
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static long setIncr(String key, int cacheSeconds) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.incr(key);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
            logger.error("set " + key + " = " + result);
        } finally {
            jedis.close();
        }
        return result;
    }

    /**
     * 模糊匹配key值（如果有键“foo”和“foobar”，命令“keys foo*”将返回“foo foobar”）
     * 
     * @param key
     * @return
     */
    public static Set<String> keys(String key) {
        Set<String> value = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (jedis.exists(key)) {
                value = jedis.keys(key);
            }
        } catch (Exception e) {
            logger.error("get " + key + " = " + value);
        } finally {
            jedis.close();
        }
        return value;
    }

    /**
     * 获取缓存
     * 
     * @param key 键
     * @return 值
     */
    public static String get(String key) {
        String value = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (jedis.exists(key)) {
                value = jedis.get(key);
                // Redis里执行get或hget不存在的key或field时返回值在终端显式的是”(nil)”
                value = StringUtil.isNotBlank(value) && !"nil".equalsIgnoreCase(value) ? value : null;
            }
        } catch (Exception e) {
            logger.error("get " + key + " = " + value);
        } finally {
            jedis.close();
        }
        return value;
    }

    /**
     * 获取缓存
     * 
     * @param key 键
     * @return 值
     */
    public static byte[] get(byte[] key) {
        byte[] value = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (jedis.exists(key)) {
                value = jedis.get(key);
            }
        } catch (Exception e) {
            logger.error("get " + key + " = " + value);
        } finally {
            jedis.close();
        }
        return value;
    }

    /**
     * 获取缓存
     * 
     * @param key 键
     * @return 值
     */
    public static Object getObject(String key, Class<?> clazz) {
        Object value = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (jedis.exists(JSON.toJSONBytes(key))) {
                value = JSON.parseObject(((JSONObject) JSON.parse(jedis.get(JSON.toJSONBytes(key)))).toString(), clazz);
            }
        } catch (Exception e) {
            logger.error("getObject " + key + " = " + value);
        } finally {
            jedis.close();
        }
        return value;
    }

    /**
     * 设置缓存
     * 
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static String set(String key, String value, int cacheSeconds) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.set(key, value);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
            logger.error("set " + key + " = " + value);
        } finally {
            jedis.close();
        }
        return result;
    }

    /**
     * 设置缓存
     * 
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static String set(byte[] key, byte[] value, int cacheSeconds) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.set(key, value);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
            logger.error("set " + key + " = " + value);
        } finally {
            jedis.close();
        }
        return result;
    }

    /**
     * 设置缓存
     * 
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static String setObject(String key, Object value, int cacheSeconds) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.set(JSON.toJSONBytes(key), JSON.toJSONBytes(value));
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
            logger.error("setObject " + key + " = " + value, e);
        } finally {
            jedis.close();
        }
        return result;
    }

    /**
     * 获取List缓存
     * 
     * @param key 键
     * @return 值
     */
    public static List<String> getList(String key) {
        List<String> value = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (jedis.exists(key)) {
                value = jedis.lrange(key, 0, -1);
            }
        } catch (Exception e) {
            logger.error("getList " + key + " = " + value);
        } finally {
            jedis.close();
        }
        return value;
    }

    /**
     * 获取List缓存
     * 
     * @param key 键
     * @return 值
     */
    public static List<Object> getObjectList(String key, Class<?> clazz) {
        List<Object> value = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (jedis.exists(JSON.toJSONBytes(key))) {
                List<byte[]> list = jedis.lrange(JSON.toJSONBytes(key), 0, -1);
                value = new ArrayList<Object>();
                for (byte[] bs : list) {
                    value.add(JSON.parseObject(((JSONObject) JSON.parse(bs)).toString(), clazz));
                }
            }
        } catch (Exception e) {
            logger.error("getObjectList " + key + " = " + value);
        } finally {
            jedis.close();
        }
        return value;
    }

    /**
     * 设置List缓存
     * 
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static long setList(String key, List<String> value, int cacheSeconds) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (jedis.exists(key)) {
                jedis.del(key);
            }
            result = jedis.rpush(key, (String[]) value.toArray());
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
            logger.error("setList " + key + " = " + value);
        } finally {
            jedis.close();
        }
        return result;
    }

    /**
     * 设置List缓存
     * 
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static long setObjectList(String key, List<Object> value, int cacheSeconds) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (jedis.exists(JSON.toJSONBytes(key))) {
                jedis.del(key);
            }
            List<byte[]> list = new ArrayList<byte[]>();
            for (Object o : value) {
                list.add(JSON.toJSONBytes(o));
            }
            result = jedis.rpush(JSON.toJSONBytes(key), (byte[][]) list.toArray());
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
            logger.error("setObjectList " + key + " = " + value);
        } finally {
            jedis.close();
        }
        return result;
    }

    /**
     * 向List缓存中添加值
     * 
     * @param key 键
     * @param value 值
     * @return
     */
    public static long listAdd(String key, String... value) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.rpush(key, value);
        } catch (Exception e) {
            logger.error("listAdd " + key + " = " + value);
        } finally {
            jedis.close();
        }
        return result;
    }

    /**
     * 向List缓存中添加值
     * 
     * @param key 键
     * @param value 值
     * @return
     */
    public static long listObjectAdd(String key, Object... value) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            List<byte[]> list = new ArrayList<byte[]>();
            for (Object o : value) {
                list.add(JSON.toJSONBytes(o));
            }
            result = jedis.rpush(JSON.toJSONBytes(key), (byte[][]) list.toArray());
        } catch (Exception e) {
            logger.error("listObjectAdd " + key + " = " + value);
        } finally {
            jedis.close();
        }
        return result;
    }

    /**
     * 获取缓存
     * 
     * @param key 键
     * @return 值
     */
    public static Set<String> getSet(String key) {
        Set<String> value = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (jedis.exists(key)) {
                value = jedis.smembers(key);
            }
        } catch (Exception e) {
            logger.error("getSet " + key + " = " + value);
        } finally {
            jedis.close();
        }
        return value;
    }

    /**
     * 获取缓存
     * 
     * @param key 键
     * @return 值
     */
    public static Set<Object> getObjectSet(String key) {
        Set<Object> value = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (jedis.exists(JSON.toJSONBytes(key))) {
                value = new HashSet<Object>();
                Set<byte[]> set = jedis.smembers(JSON.toJSONBytes(key));
                for (byte[] bs : set) {
                    value.add(JSON.toJSONBytes(bs));
                }
            }
        } catch (Exception e) {
            logger.error("getObjectSet " + key + " = " + value);
        } finally {
            jedis.close();
        }
        return value;
    }

    /**
     * 设置Set缓存
     * 
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static long setSet(String key, Set<String> value, int cacheSeconds) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (jedis.exists(key)) {
                jedis.del(key);
            }
            result = jedis.sadd(key, (String[]) value.toArray());
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
            logger.error("setSet " + key + " = " + value);
        } finally {
            jedis.close();
        }
        return result;
    }

    /**
     * 设置Set缓存
     * 
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static long setObjectSet(String key, Set<Object> value, int cacheSeconds) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (jedis.exists(JSON.toJSONBytes(key))) {
                jedis.del(key);
            }
            Set<byte[]> set = new HashSet<byte[]>();
            for (Object o : value) {
                set.add(JSON.toJSONBytes(o));
            }
            result = jedis.sadd(JSON.toJSONBytes(key), (byte[][]) set.toArray());
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
            logger.error("setObjectSet " + key + " = " + value);
        } finally {
            jedis.close();
        }
        return result;
    }

    /**
     * 向Set缓存中添加值
     * 
     * @param key 键
     * @param value 值
     * @return
     */
    public static long setSetAdd(String key, String... value) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.sadd(key, value);
        } catch (Exception e) {
            logger.error("setSetAdd " + key + " = " + value);
        } finally {
            jedis.close();
        }
        return result;
    }

    /**
     * 向Set缓存中添加值
     * 
     * @param key 键
     * @param value 值
     * @return
     */
    public static long setSetObjectAdd(String key, Object... value) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Set<byte[]> set = new HashSet<byte[]>();
            for (Object o : value) {
                set.add(JSON.toJSONBytes(o));
            }
            result = jedis.rpush(JSON.toJSONBytes(key), (byte[][]) set.toArray());
        } catch (Exception e) {
            logger.error("setSetObjectAdd " + key + " = " + value);
        } finally {
            jedis.close();
        }
        return result;
    }

    /**
     * 获取Map缓存
     * 
     * @param key 键
     * @return 值
     */
    public static Map<String, String> getMap(String key) {
        Map<String, String> value = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (jedis.exists(key)) {
                value = jedis.hgetAll(key);
            }
        } catch (Exception e) {
            logger.error("getMap " + key + " = " + value);
        } finally {
            jedis.close();
        }
        return value;
    }

    /**
     * 获取Map缓存
     * 
     * @param key 键
     * @return 值
     */
    public static Map<String, Object> getObjectMap(String key) {
        Map<String, Object> value = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (jedis.exists(JSON.toJSONBytes(key))) {
                value = new HashMap<String, Object>();
                Map<byte[], byte[]> map = jedis.hgetAll(JSON.toJSONBytes(key));
                for (Map.Entry<byte[], byte[]> e : map.entrySet()) {
                    value.put(StringUtil.toString(e.getKey(), "UTF-8"),
                              JSON.parseObject(((JSONObject) JSON.parse(e.getValue())).toString(), HashMap.class));
                }
            }
        } catch (Exception e) {
            logger.error("getObjectMap " + key + " = " + value);
        } finally {
            jedis.close();
        }
        return value;
    }

    /**
     * 设置Map缓存
     * 
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static String setMap(String key, Map<String, String> value, int cacheSeconds) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (jedis.exists(key)) {
                jedis.del(key);
            }
            result = jedis.hmset(key, value);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
            logger.error("setMap " + key + " = " + value);
        } finally {
            jedis.close();
        }
        return result;
    }

    /**
     * 设置Map缓存
     * 
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static String setObjectMap(String key, Map<String, Object> value, int cacheSeconds) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (jedis.exists(JSON.toJSONBytes(key))) {
                jedis.del(key);
            }
            Map<byte[], byte[]> map = new HashMap<byte[], byte[]>();
            for (Map.Entry<String, Object> e : value.entrySet()) {
                map.put(JSON.toJSONBytes(e.getKey()), JSON.toJSONBytes(e.getValue()));
            }
            result = jedis.hmset(JSON.toJSONBytes(key), (Map<byte[], byte[]>) map);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
            logger.error("setObjectMap " + key + " = " + value);
        } finally {
            jedis.close();
        }
        return result;
    }

    /**
     * 向Map缓存中添加值
     * 
     * @param key 键
     * @param value 值
     * @return
     */
    public static String mapPut(String key, Map<String, String> value) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.hmset(key, value);
            logger.debug("mapPut " + key + " = " + value);
        } catch (Exception e) {
            logger.error("mapPut " + key + " = " + value);
        } finally {
            jedis.close();
        }
        return result;
    }

    /**
     * 向Map缓存中添加值
     * 
     * @param key 键
     * @param value 值
     * @return
     */
    public static String mapObjectPut(String key, Map<String, Object> value) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Map<byte[], byte[]> map = new HashMap<byte[], byte[]>();
            for (Map.Entry<String, Object> e : value.entrySet()) {
                map.put(JSON.toJSONBytes(e.getKey()), JSON.toJSONBytes(e.getValue()));
            }
            result = jedis.hmset(JSON.toJSONBytes(key), (Map<byte[], byte[]>) map);
        } catch (Exception e) {
            logger.error("mapObjectPut " + key + " = " + value);
        } finally {
            jedis.close();
        }
        return result;
    }

    /**
     * 移除Map缓存中的值
     * 
     * @param key 键
     * @param value 值
     * @return
     */
    public static long mapRemove(String key, String mapKey) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.hdel(key, mapKey);
        } catch (Exception e) {
            logger.error("mapRemove " + key + " = " + mapKey);
        } finally {
            jedis.close();
        }
        return result;
    }

    /**
     * 移除Map缓存中的值
     * 
     * @param key 键
     * @param value 值
     * @return
     */
    public static long mapObjectRemove(String key, String mapKey) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.hdel(JSON.toJSONBytes(key), JSON.toJSONBytes(mapKey));
        } catch (Exception e) {
            logger.error("mapObjectRemove " + key + " = " + mapKey);
        } finally {
            jedis.close();
        }
        return result;
    }

    /**
     * 判断Map缓存中的Key是否存在
     * 
     * @param key 键
     * @param value 值
     * @return
     */
    public static boolean mapExists(String key, String mapKey) {
        boolean result = false;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.hexists(key, mapKey);
        } catch (Exception e) {
            logger.error("mapExists " + key + " = " + mapKey);
        } finally {
            jedis.close();
        }
        return result;
    }

    /**
     * 判断Map缓存中的Key是否存在
     * 
     * @param key 键
     * @param value 值
     * @return
     */
    public static boolean mapObjectExists(String key, String mapKey) {
        boolean result = false;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.hexists(JSON.toJSONBytes(key), JSON.toJSONBytes(mapKey));
        } catch (Exception e) {
            logger.error("mapObjectExists " + key + " = " + mapKey);
        } finally {
            jedis.close();
        }
        return result;
    }

    /**
     * 删除缓存
     * 
     * @param key 键
     * @return
     */
    public static long del(String key) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (jedis.exists(key)) {
                result = jedis.del(key);
            } else {
                logger.warn("del " + key + "not exists");
            }
        } catch (Exception e) {
            logger.error("del " + key);
        } finally {
            jedis.close();
        }
        return result;
    }

    /**
     * 删除缓存
     * 
     * @param key 键
     * @return
     */
    public static long del(String[] key) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.del(key);
        } catch (Exception e) {
            logger.error("del " + key);
        } finally {
            jedis.close();
        }
        return result;
    }

    /**
     * 删除缓存
     * 
     * @param key 键
     * @return
     */
    public static long delObject(String key) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (jedis.exists(key)) {
                result = jedis.del(key);
            } else {
                logger.warn("delObject " + key + "not exists");
            }
        } catch (Exception e) {
            logger.error("delObject " + key + "not exists");
        } finally {
            jedis.close();
        }
        return result;
    }

    /**
     * 缓存是否存在
     * 
     * @param key 键
     * @return
     */
    public static boolean exists(String key) {
        boolean result = false;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.exists(key);
        } catch (Exception e) {
            logger.error("exists " + key);
        } finally {
            jedis.close();
        }
        return result;
    }

    /**
     * 缓存是否存在
     * 
     * @param key 键
     * @return
     */
    public static boolean existsObject(String key) {
        boolean result = false;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.exists(JSON.toJSONBytes(key));
        } catch (Exception e) {
            logger.error("existsObject " + key);
        } finally {
            jedis.close();
        }
        return result;
    }

}
