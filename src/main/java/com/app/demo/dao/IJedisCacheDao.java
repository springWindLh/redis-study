package com.app.demo.dao;

import java.util.List;

/**
 * Created by lh on 2017/3/20.
 */

public interface IJedisCacheDao<T> {
    String get(String key);

    String set(String key, String value);

    String set(String key, String value, int cacheSeconds);

    T getObject(String key);

    String setObject(String key, T value);

    String setObject(String key, T value, int cacheSeconds);

    List<String> getList(String key);

    Long setList(String key, List<String> value);

    Long setList(String key, List<String> value, int cacheSeconds);

    List<T> getObjectList(String key);

    Long setObjectList(String key, List<T> value);

    Long setObjectList(String key, List<T> value, int cacheSeconds);

    Long del(String key);
}
