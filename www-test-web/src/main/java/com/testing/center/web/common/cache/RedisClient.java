package com.testing.center.web.common.cache;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing.center.web.common.utils.cdn.Compressor;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * redis 帮助类
 */
public class RedisClient {

    static final Logger LOGGER = LoggerFactory.getLogger(RedisClient.class);
    static Random random = new Random();
    Compressor _compressor = new Compressor();
    private Charset _encoding = StandardCharsets.UTF_8;
    //    private int zipMinLength = 2 * 1024;
    private int zipMinLength = 1024 * 1024 * 1024;

    /**
     * redis连接池
     */
    private JedisPool[] pools;

    protected RedisClient(JedisPool... jedisPool) {
        this.pools = jedisPool;
    }

    /**
     * redis job key
     */
    static final String REDIS_JOB_KEY = "_redis_job";

    private Jedis getJedis(String key) {

        int poolSize = pools.length;

        int mod = key.hashCode() % (poolSize - 1);

        return pools[Math.abs(mod)].getResource();

    }

    static synchronized int getRanomMilles() {
        int num = random.nextInt(600);
        return num;
    }

    /**
     * 获取缓存数据
     *
     * @param key
     * @return
     */
    <T> T get(String key, TypeReference<T> typeReference) {
        Jedis jedis = null;
        try {
            jedis = getJedis(key);
            String val = jedis.get(key);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("redis get()...key:" + key + ",val:" + val);
            }

            // 没有获取到val,返回空
            if (StringUtils.isEmpty(val)) {
                return null;
            }

            ObjectMapper mapper = new ObjectMapper();
            //获取到空对象，返回空的缓存对象
            if (StringUtils.equals(val, "{}")) {

                return (T) mapper.readValue(val, EmptyCacheObject.class);
            }

            return (T) mapper.readValue(val, typeReference);
        } catch (IOException ex) {
            LOGGER.error("get() ERROR!,[key:" + key + "],Exception Message:" + ex.getMessage(), ex);
        } finally {

            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }

    /**
     * 获取hash field缓存数据
     *
     * @param key
     * @param field
     * @param typeReference
     * @param <T>
     * @return
     */
    <T> T hget(String key, String field, TypeReference<T> typeReference) {
        Jedis jedis = null;
        try {
            jedis = getJedis(key);
            String val = jedis.hget(key, field);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("redis hget()...key:" + key + ",val:" + val);
            }

            // 没有获取到val,返回空
            if (StringUtils.isEmpty(val) || val.equals("nil")) {
                return null;
            }

            ObjectMapper mapper = new ObjectMapper();
            //获取到空对象，返回空的缓存对象
            if (StringUtils.equals(val, "{}")) {

                return (T) mapper.readValue(val, EmptyCacheObject.class);
            }

            return (T) mapper.readValue(val, typeReference);
        } catch (IOException ex) {
            LOGGER.error("get() ERROR!,[key:" + key + "],Exception Message:" + ex.getMessage(), ex);
        } finally {

            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }

    /**
     * 获取缓存数据
     *
     * @param key
     * @return
     */
    <T> T getAndReload(String key, int expire, TypeReference<T> typeReference, CacheCaller<T> cacheCaller) {
        long startTime = System.currentTimeMillis();
        Jedis jedis = null;
        try {
            jedis = getJedis(key);
            String val = jedis.get(key);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("redis get()...key:" + key + ",val:" + val);
            }
//            LOGGER.info("getAndReload val="+ val);
            // 没有获取到val,返回空
            if (StringUtils.isBlank(val)) {
                logOverTime("getAndReload_redis_return_blank", startTime, key);
                return null;
            }

            /*long ttl = jedis.ttl(key);

             *//**
             * 缓存时间还剩下1分钟的时候异步reloadCache。
             * 有效过期时间必须不低于2分钟，避免短缓存key每次reload。
             *//*
            if (ttl < 60 && expire >= 60 * 2) {
                reloadCache(key, expire, cacheCaller, "");
            }*/

            ObjectMapper mapper = new ObjectMapper();
            //获取到空对象，返回空的缓存对象
            if (StringUtils.equals(val, "{}")) {
                T obj = (T) mapper.readValue(val, EmptyCacheObject.class);
                logOverTime("getAndReload_redis_return_empty", startTime, key);
                return obj;
            }
            T obj = (T) mapper.readValue(val, typeReference);
            logOverTime("getAndReload", startTime, key);
            return obj;
        } catch (IOException ex) {
            LOGGER.error("get() ERROR!,[key:" + key + "],Exception Message:" + ex.getMessage(), ex);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        logOverTime("getAndReload_error", startTime, key);
        return null;
    }

    /**
     * 获取Hash缓存数据
     *
     * @param key
     * @return
     */
    <T> T getAndReload(String key, String field, int expire, TypeReference<T> typeReference, CacheCaller<Map> cacheCaller) {
        long startTime = System.currentTimeMillis();
        Jedis jedis = null;
        try {
            jedis = getJedis(key);
            String val = jedis.hget(key, field);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("redis hash getAndReload()...key:" + key + ",field:" + field + ",val:" + val);
            }

            ObjectMapper mapper = new ObjectMapper();

            // 没有获取到val,返回空
            if (StringUtils.isBlank(field) && jedis.exists(key)) {  // 如果key存在field不存在，直接返回空对象
                T obj = (T) mapper.readValue(val, EmptyCacheObject.class);
                logOverTime("getAndReload2_redis_key_exist_value_blank", startTime, key);
                return obj;
            } else if (StringUtils.isBlank(val)) {
                logOverTime("getAndReload2_redis_return_blank", startTime, key);
                return null;
            }
            /*long ttl = jedis.ttl(key);

             *//**
             * 缓存时间还剩下1分钟的时候异步reloadCache。
             * 有效过期时间必须不低于2分钟，避免短缓存key每次reload。
             *//*
            if (ttl < 60 && expire >= 60 * 2) {
                reloadCache(key, expire, cacheCaller, "hash");
            }
*/
            //获取到空对象，返回空的缓存对象
            if (StringUtils.equals(val, "{}")) {
                T obj = (T) mapper.readValue(val, EmptyCacheObject.class);
                logOverTime("getAndReload2_redis_return_empty", startTime, key);
                return obj;
            }
            T obj = (T) mapper.readValue(val, typeReference);
            logOverTime("getAndReload2", startTime, key);
            return obj;
        } catch (IOException ex) {
            LOGGER.error("get() ERROR!,[key:" + key + "],Exception Message:" + ex.getMessage(), ex);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        logOverTime("getAndReload2_error", startTime, key);
        return null;
    }

    /**
     * 添加缓存数据
     *
     * @param key
     * @param value
     * @param expire
     */
    public <T> void setEx(String key, T value, int expire) {
        Jedis jedis = null;
        try {
            jedis = getJedis(key);
            String result = new ObjectMapper().writeValueAsString(value);
            jedis.setex(key, expire, result);
            if (result.length() > 10 * 1024) {
                LOGGER.warn("redis value too long --" + key + ",length--" + result.length());
            }
        } catch (IOException ex) {
            LOGGER.error("setEx() ERROR!,[key:" + key + "],[value:" + value + "],Exception Message:" + ex.getMessage(), ex);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 添加hash缓存数据
     *
     * @param key
     * @param value
     * @param expire
     */
    public void hsetEx(String key, Map value, int expire) {
        Jedis jedis = null;
        try {
            jedis = getJedis(key);
            /*
            Map<String, String> val = new HashMap();
            for (String k : (Set<String>)m.keySet()) {
                Object obj = m.get(k);
                val.put(k, new ObjectMapper().writeValueAsString(obj));
            }*/
            jedis.hmset(key, value);
            jedis.expire(key, expire);
        } catch (Exception ex) {
            LOGGER.error("hsetEx() ERROR!,[key:" + key + "],[value:" + value + "],Exception Message:" + ex.getMessage(), ex);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 删除缓存中得对象，根据key
     *
     * @param key
     * @return
     */
    public boolean del(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis(key);
            jedis.del(key);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * ttl
     *
     * @param key
     * @return
     */
    private long ttl(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis(key);
            return jedis.ttl(key);

        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * existskey
     *
     * @param key
     * @return
     */
    public boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis(key);
            return jedis.exists(key);

        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 删除缓存所有对象
     *
     * @return
     */
    public boolean clearAll() {
        for (JedisPool pool : pools) {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                jedis.flushDB();

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
        return true;
    }

    /**
     * 对于超时的函数，进行log
     *
     * @param name       函数名
     * @param startTime  开始时间
     * @param additional 附加信息
     */
    private void logOverTime(String name, long startTime, String additional) {
        long endTime = System.currentTimeMillis();
        long diff = endTime - startTime;
        if (diff > 5000) {
            long threadId = Thread.currentThread().getId();
            LOGGER.warn(name + " thread id : " + threadId + " overtime : " + diff + ", " + additional);
        }
    }

    /**
     * 尝试从 Redis 获取数据，无数据时获取数据并存入 Redis
     *
     * @param key
     * @param expire
     * @param typeReference
     * @param caller
     * @param <T>
     * @return
     */
    public <T> T getAndSet(String key, int expire, TypeReference<T> typeReference, CacheCaller<T> caller) {
        // 返回空值
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        long startTime = System.currentTimeMillis();
        T returnObj = getAndReload(key, expire + getRanomMilles(), typeReference, caller);
        // 缓存存在数据，直接返回
        if (null != returnObj) {
            // 如果缓存数据为空对象，返回null
            if (returnObj instanceof EmptyCacheObject) {
                logOverTime("getAndSet_redis_return_null", startTime, key);
                return null;
            }
            logOverTime("getAndSet", startTime, key);
            return (T) returnObj;
        }
        logOverTime("getAndSet_after_getAndReload", startTime, key);
        // 已经有任务在执行 , 最多获取缓存10次，有获取到数据直接返回，都没有获取到数据直接落地到数据库查询
        if (checkKey(REDIS_JOB_KEY + "_" + key)) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                LOGGER.error("getAndSet() InterruptedException error : " + e.getMessage());
            }
            returnObj = get(key, typeReference);
            if (null != returnObj) {
                // 如果缓存数据为空对象，返回null
                if (returnObj instanceof EmptyCacheObject) {
                    logOverTime("getAndSet_wait_1000ms_return_null", startTime, key);
                    return null;
                }
            }
            logOverTime("getAndSet_wait_1000ms", startTime, key);
            return returnObj;
        } else { //没有任务在执行,调用回调接口
            addJob(REDIS_JOB_KEY + "_" + key);
            try {
                T o = caller.getData();
                // 数据为空的话往缓存里保存空对象，防止缓存被击穿
                if (null != o) {
                    setEx(key, o, expire + getRanomMilles());
                    logOverTime("getAndSet_no_job", startTime, key);
                    return o;
                } else {
                    //如果为空设置缓存时间为2分钟
                    int expireTime = 120;
                    setEx(key, EmptyCacheObject.instance, expireTime);
                    logOverTime("getAndSet_no_job_return_null", startTime, key);
                    return null;
                }
            } catch (Exception e) {
                LOGGER.error("getAndSet() error:" + e.getMessage(), e);
            } finally {
                removeJob(REDIS_JOB_KEY + "_" + key);
            }
        }
        logOverTime("getAndSet_no_job_error", startTime, key);
        return null;
    }

    public <T> T hGetAndSet(String key, String field, int expire, TypeReference<T> typeReference, CacheCaller<Map> caller) {

        // 返回空值
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(field)) {
            LOGGER.info(key + "-hGetAndSet() isEmpty field=" + field);
            return null;
        }

        long startTime = System.currentTimeMillis();
        T returnObj = getAndReload(key, field, expire + getRanomMilles(), typeReference, caller);
        // 缓存存在数据，直接返回
        if (null != returnObj) {
            // 如果缓存数据为空对象，返回null
            if (returnObj instanceof EmptyCacheObject) {
                logOverTime("hGetAndSet_redis_return_null", startTime, key);
                return null;
            }
            logOverTime("hGetAndSet", startTime, key);
            return (T) returnObj;
        }
        logOverTime("hGetAndSet_after_getAndReload", startTime, key);

        // 已经有任务在执行 , 最多获取缓存10次，有获取到数据直接返回，都没有获取到数据直接落地到数据库查询
        if (checkKey(REDIS_JOB_KEY + "_" + key)) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                LOGGER.error("hgetAndSet() InterruptedException error:" + e.getMessage());
            }
            returnObj = hget(key, field, typeReference);
            if (null != returnObj) {
                // 如果缓存数据为空对象，返回null
                if (returnObj instanceof EmptyCacheObject) {
                    logOverTime("hGetAndSet_wait_1000ms_return_null", startTime, key);
                    return null;
                }
            }
            logOverTime("hGetAndSet_wait_1000ms", startTime, key);
            return returnObj;
        } else { //没有任务在执行,调用回调接口
            addJob(REDIS_JOB_KEY + "_" + key);
            try {
                Map o = caller.getData();
                // 数据为空的话往缓存里保存空对象，防止缓存被击穿
                if (null != o) {
                    hsetEx(key, o, expire + getRanomMilles());
                    String val = (String) o.get(field);
                    if (StringUtils.isNotBlank(val) && val.length() > 10 * 1024) {
                        LOGGER.warn("val too long --" + key + ",length--" + val.length());
                    }
                    if (StringUtils.isNotBlank(val) && val.trim().length() > 0) {
                        logOverTime("hGetAndSet_no_job", startTime, key);
                        return (T) new ObjectMapper().readValue(val, typeReference);
                    }
                } else {
                    //TODO 怎么处理
                    o.put(field, EmptyCacheObject.instance);
                    hsetEx(key, o, expire + getRanomMilles());
                    logOverTime("hGetAndSet_no_job_return_null", startTime, key);
                    return null;
                }
            } catch (Exception e) {
                LOGGER.error("getAndSet() error:" + e.getMessage(), e);
            } finally {
                removeJob(REDIS_JOB_KEY + "_" + key);
            }
        }
        logOverTime("hGetAndSet_no_job_error", startTime, key);
        return null;
    }

    protected boolean checkKey(String key) {
        return EhcacheHelper.checkkey(key);
    }

    protected void addJob(String key) {
        EhcacheHelper.addjob(key);
    }

    protected void removeJob(String key) {
        EhcacheHelper.removeJob(key);
    }

    public void destory() {
        for (JedisPool pool : pools) {
            pool.destroy();
        }
        this.reloadPoolExecutor.shutdown();
    }

    private ThreadPoolExecutor reloadPoolExecutor = new ThreadPoolExecutor(10, 20, 0l, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(10000), new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            LOGGER.warn("reloadPoolExecutor is full,task will be discard...");
        }
    });

    private AtomicLong reloadCount = new AtomicLong();

    private static final String REDIS_RELOAD_KEY_PREFIX = "redis_reload_";

    private <T> void reloadCache(final String key, final int expire,
                                 final CacheCaller<T> caller, final String type) {

        if (reloadCount.getAndIncrement() % 1000 == 0) {
            LOGGER.info("reloadCache count is :" + reloadCount.get() + ";reloadPoolExecutor.active:" + reloadPoolExecutor.getActiveCount()
                    + ";reloadPoolExecutor.taskCount:" + reloadPoolExecutor.getTaskCount() + ";reloadPoolExecutor.completedTaskCount" + reloadPoolExecutor.getCompletedTaskCount());
        }

        reloadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                if (!exists(REDIS_RELOAD_KEY_PREFIX + key)) {
                    //60s过期，不主动移除缓存
                    setEx(REDIS_RELOAD_KEY_PREFIX + key, "1", 60);
                    T t = caller.getData();
                    if (type.equals("hash")) {
                        hsetEx(key, (Map) t, expire);
                    } else {
                        setEx(key, t, expire);
                    }
                }
            }
        });

    }

    /**
     * redis 执行方法
     *
     * @param execCaller
     * @param <T>
     * @return
     */
    public <T> T exec(String key, ExecCaller<T> execCaller) {
        Jedis jedis = getJedis(key);
        try {
            return execCaller.exec(jedis);
        } finally {
            jedis.close();
        }
    }

    /**
     * 指定redis进行执行
     *
     * @param index
     * @param key
     * @param execCaller
     * @param <T>
     * @return
     */
    public <T> T exec(int index, String key, ExecCaller<T> execCaller) {
        Jedis jedis = pools[index].getResource();
        try {
            return execCaller.exec(jedis);
        } finally {
            jedis.close();
        }
    }

    /**
     * 删除模糊匹配的key对象 add by luozi
     *
     * @param keyPattern 模糊匹配的key_*
     * @return
     */
    public boolean delKeyPattern(String keyPattern) {
        boolean temp = true;
        if (pools != null && pools.length > 0) {
            for (JedisPool pool : pools) {
                Jedis jedis = null;
                try {
                    jedis = pool.getResource();
                    if (jedis != null) {
                        Set<String> setKeys = jedis.keys(keyPattern);//得到模糊匹配的所有 key对象
                        if (setKeys != null && setKeys.size() > 0) {
                            for (String key : setKeys) {
                                jedis.del(key);
                                temp = true;
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    temp = false;
                } finally {
                    if (jedis != null) {
                        jedis.close();
                    }
                }
            }
        }
        return temp;
    }

    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis(key);
            String val = jedis.get(key);
            return val;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void incr(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis(key);
            jedis.incr(key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * redis set 添加
     *
     * @param key
     * @param exists
     * @param members
     */
    public void setAddAllEx(String key, int exists, String... members) {
        Jedis jedis = null;
        try {
            jedis = getJedis(key);
            jedis.sadd(key, members);
            jedis.expire(key, exists);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * redis set 判断是否存在
     *
     * @param key
     * @param members
     * @return
     */
    public boolean setSismember(String key, String members) {
        Jedis jedis = null;
        try {
            jedis = getJedis(key);
            return jedis.sismember(key, members);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return false;
    }

    /**
     * redis set 获取所有数据
     *
     * @param key
     * @return
     */
    public Set<String> setSmembers(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis(key);
            return jedis.smembers(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * redis set
     * 删除指定值
     *
     * @param key
     * @param members
     * @return
     */
    public Long setRem(String key, String members) {
        Jedis jedis = null;
        try {
            jedis = getJedis(key);
            return jedis.srem(key, members);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * 返回哈希表 key 中给定域 field 的值。
     *
     * @param key
     * @param field
     * @return
     */
    public String hashGet(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = getJedis(key);
            String val = jedis.hget(key, field);
            if (StringUtils.isEmpty(val) || val.equals("nil")) {
                return null;
            }
            return val;
        } catch (Exception e) {
            LOGGER.error("HASH GET ERROR!,[key:" + key + "],Exception Message:" + e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public void hashIincrBy(String key, String field, long value) {
        Jedis jedis = null;
        try {
            jedis = getJedis(key);
            jedis.hincrBy(key, field, value);
        } catch (Exception e) {
            LOGGER.error("HASH GET ERROR!,[key:" + key + "],Exception Message:" + e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * hash set
     *
     * @param key
     * @param field
     * @param value
     */
    public void hashSet(String key, String field, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis(key);
            Long hset = jedis.hset(key, field, value);
        } catch (Exception e) {
            LOGGER.error("HASH SET ERROR!,[key:" + key + "],Exception Message:" + e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * hash  删除
     *
     * @param key
     * @param field
     */
    public void hashDel(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = getJedis(key);
            jedis.hdel(key, field);
        } catch (Exception e) {
            LOGGER.error("HASH DEL ERROR!,[key:" + key + "],Exception Message:" + e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 设置过期时间
     *
     * @param key
     * @param seconds
     */
    public void expire(String key, int seconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis(key);
            jedis.expire(key, seconds);
        } catch (Exception e) {
            LOGGER.error("EXPIRE ERROR!,[key:" + key + "],Exception Message:" + e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * hash
     * 查看哈希表 key 中，给定域 field 是否存在。
     *
     * @param key
     * @param field
     * @return
     */
    public Boolean hexists(String key, String field) {
        Jedis jedis = null;
        Boolean hexists = false;
        try {
            jedis = getJedis(key);
            hexists = jedis.hexists(key, field);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return hexists;
    }

    /**
     * 尝试从 Redis 获取数据，无数据时获取数据并存入 Redis
     *
     * @param key           key
     * @param expire        超时时间
     * @param typeReference 类型
     * @param caller        回调函数
     * @param <T>           类型
     * @return 返回值
     */
    public <T> T zipGetAndSet(String key, int expire, TypeReference<T> typeReference, CacheCaller<T> caller) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        long startTime = System.currentTimeMillis();
        String value = zipGet(key);
        // 缓存存在数据，直接返回
        if (value != null) {
            return parse(key, value, typeReference, startTime, "");
        }
        logOverTime("zipGetAndSet_after_getAndReload", startTime, key);
        String lockKey = REDIS_JOB_KEY + "_" + key;
        if (checkKey(lockKey)) {
            // 已经有任务在执行
            sleep(1000);
            value = zipGet(key);
            return parse(key, value, typeReference, startTime, "_wait_1000ms");
        } else {
            //没有任务在执行,调用回调接口
            addJob(lockKey);
            try {
                T o = caller.getData();
                // 数据为空的话往缓存里保存空对象，防止缓存被击穿
                if (null != o) {
                    zipSet(key, o, expire + getRanomMilles());
                    logOverTime("zipGetAndSet_no_job", startTime, key);
                    return o;
                } else {
                    //如果为空设置缓存时间为2分钟
                    int expireTime = 120;
                    zipSet(key, EmptyCacheObject.instance, expireTime);
                    logOverTime("getAndSet_no_job_return_null", startTime, key);
                    return null;
                }
            } catch (Exception e) {
                LOGGER.error("zipGetAndSet() error", e);
                logOverTime("zipGetAndSet_no_job_error", startTime, key);
                return null;
            } finally {
                removeJob(lockKey);
            }
        }
    }

    private <T> T parse(String key, String value, TypeReference<T> typeReference, long startTime, String errMsg) {
        // 如果缓存数据为空对象，返回null
        if (value == null || value.equals("") || value.equals("{}")) {
            logOverTime("zipGetAndSet_redis_return_null" + errMsg, startTime, key);
            return null;
        }
        logOverTime("zipGetAndSet" + errMsg, startTime, key);
        try {
            ObjectMapper mapper = new ObjectMapper();
            return (T) mapper.readValue(value, typeReference);
        } catch (Exception e) {
            LOGGER.error("parse object error for key : " + key, e);
        }
        return null;
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            LOGGER.error("zipGetAndSet() InterruptedException error : " + e.getMessage());
        }
    }

    private String zipGet(String key) {
        byte[] bkey = key.getBytes(_encoding);
        try (Jedis jedis = getJedis(key)) {
            byte[] val = jedis.get(bkey);
            if (val == null || val.length == 0) {
                return null;
            }
            //防止缓存穿透
            if (val.length == 2 && val[0] == '{' && val[1] == '}') {
                return "{}";
            }
            //是gzip压缩的数据
            if (val.length > 2 && val[0] == 120 && val[1] == -100) {
                return _compressor.decode(val);
            }
            return new String(val, _encoding);
        } catch (IOException ex) {
            LOGGER.error("zipGet() ERROR!,[key:" + key + "]", ex);
        }
        return null;
    }

    private <T> void zipSet(String key, T obj, int expire) {
        byte[] bkey = key.getBytes(_encoding);
        try (Jedis jedis = getJedis(key)) {
            String result = new ObjectMapper().writeValueAsString(obj);
            if (result.length() > zipMinLength) {
                byte[] zipped = _compressor.encode(result);
                LOGGER.warn("redis value is too long key : " + key + ", length : " + result.length() + ", zipped length : " + zipped.length);
                jedis.setex(bkey, expire, zipped);
            } else {
                jedis.setex(bkey, expire, result.getBytes(_encoding));
            }
        } catch (IOException ex) {
            LOGGER.error("zipSet() ERROR!,[key:" + key + "]", ex);
        }
    }
}
