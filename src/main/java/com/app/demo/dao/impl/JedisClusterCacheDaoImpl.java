package com.app.demo.dao.impl;

import com.app.demo.dao.IJedisCacheDao;
import com.app.demo.dao.support.SerializeUtils;
import com.app.demo.jedis.JedisClusterFactory;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lh on 2017/3/23.
 */
@Repository("jedisClusterCacheDao")
public class JedisClusterCacheDaoImpl<T> implements IJedisCacheDao<T> {
    @Autowired
    private JedisClusterFactory jedisClusterFactory;

    public JedisCluster getJedisCluster() {
        JedisCluster jedisCluster = null;
        try {
            jedisCluster = jedisClusterFactory.getJedisCluster();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jedisCluster;
    }

    @Override
    public String get(String key) {
        String value = null;
        JedisCluster jedisCluster = null;
        try {
            jedisCluster = getJedisCluster();
            if (jedisCluster.exists(key)) {
                value = jedisCluster.get(key);
                value = StringUtils.isNotBlank(value) ? value : null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeJedisCluster(jedisCluster);
        }
        return value;
    }

    @Override
    public String set(String key, String value, int cacheSeconds) {
        String result = null;
        JedisCluster jedisCluster = null;
        try {
            jedisCluster = getJedisCluster();
            result = jedisCluster.set(key, value);
            if (cacheSeconds != 0) {
                jedisCluster.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeJedisCluster(jedisCluster);
        }
        return result;
    }

    @Override
    public String set(String key, String value) {
        return set(key, value, 0);
    }

    /**
     * 设置缓存
     *
     * @param key          String
     * @param value        Object对象
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    @Override
    public String setObject(String key, T value, int cacheSeconds) {
        String result = null;
        JedisCluster jedisCluster = null;
        try {
            jedisCluster = getJedisCluster();
            result = jedisCluster.set(key.getBytes(), SerializeUtils.serialize(value));
            if (cacheSeconds != 0) {
                jedisCluster.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeJedisCluster(jedisCluster);
        }
        return result;
    }

    @Override
    public String setObject(String key, T value) {
        return setObject(key, value, 0);
    }

    /**
     * 获取缓存
     *
     * @param key
     * @return 对象(反序列化)
     */
    @Override
    public T getObject(String key) {
        T value = null;
        JedisCluster jedisCluster = null;
        try {
            jedisCluster = getJedisCluster();
            byte[] bytes = jedisCluster.get(key.getBytes());
            value = (T) SerializeUtils.deSerialize(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeJedisCluster(jedisCluster);
        }
        return value;
    }

    /**
     * 获取list缓存
     *
     * @param key
     * @return
     */
    @Override
    public List<String> getList(String key) {
        List<String> value = null;
        JedisCluster jedisCluster = null;
        try {
            jedisCluster = getJedisCluster();
            value = jedisCluster.lrange(key, 0, -1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeJedisCluster(jedisCluster);
        }
        return value;
    }

    /**
     * 获取list缓存，元素是object
     *
     * @param key
     * @return
     */
    @Override
    public List<T> getObjectList(String key) {
        List<T> value = null;
        JedisCluster jedisCluster = null;
        try {
            jedisCluster = getJedisCluster();
            List<byte[]> list = jedisCluster.lrange(key.getBytes(), 0, -1);
            value = Lists.newArrayList();
            for (byte[] bytes : list) {
                value.add((T) SerializeUtils.deSerialize(bytes));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeJedisCluster(jedisCluster);
        }
        return value;
    }

    /**
     * 设置list缓存
     *
     * @param key
     * @param value
     * @param cacheSeconds
     * @return
     */
    @Override
    public Long setList(String key, List<String> value, int cacheSeconds) {
        long result = 0;
        JedisCluster jedisCluster = null;
        try {
            jedisCluster = getJedisCluster();
            jedisCluster.del(key);
            String[] arr = new String[value.size()];
            value.toArray(arr);
            result = jedisCluster.rpush(key, arr);
            if (cacheSeconds != 0) {
                jedisCluster.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeJedisCluster(jedisCluster);
        }
        return result;
    }

    @Override
    public Long setList(String key, List<String> value) {
        return setList(key, value, 0);
    }

    /**
     * 设置list缓存，list的元素为object
     *
     * @param key
     * @param value
     * @param cacheSeconds
     * @return
     */
    @Override
    public Long setObjectList(String key, List<T> value, int cacheSeconds) {
        long result = 0;
        JedisCluster jedisCluster = null;
        try {
            jedisCluster = getJedisCluster();
            jedisCluster.del(key);
            ArrayList<byte[]> list = Lists.newArrayList();
            for (Object o : value) {
                list.add(SerializeUtils.serialize(o));
            }
            byte[][] arr = new byte[list.size()][];
            list.toArray(arr);
            result = jedisCluster.rpush(key.getBytes(), arr);
            if (cacheSeconds != 0) {
                jedisCluster.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeJedisCluster(jedisCluster);
        }
        return result;
    }

    @Override
    public Long setObjectList(String key, List<T> value) {
        return setObjectList(key, value, 0);
    }

    /**
     * 删除key
     *
     * @param key
     * @return
     */
    @Override
    public Long del(String key) {
        long result = 0;
        JedisCluster jedisCluster = null;
        try {
            jedisCluster = getJedisCluster();
            result = jedisCluster.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeJedisCluster(jedisCluster);
        }
        return result;
    }

    private void closeJedisCluster(JedisCluster jedisCluster) {
        if (jedisCluster != null) {
            try {
                jedisCluster.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
