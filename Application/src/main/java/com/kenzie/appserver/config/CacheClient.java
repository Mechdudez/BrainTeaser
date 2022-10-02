package com.kenzie.appserver.config;

import com.amazonaws.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.kenzie.appserver.service.model.Category;

import java.util.concurrent.TimeUnit;

public class CacheClient {

    private Cache<String, Category> categoryCache;

    // Help from Dan Sun
    public CacheClient(int expireTime, TimeUnit timeUnit){
        this.categoryCache = CacheBuilder.newBuilder()
                .expireAfterWrite(expireTime,timeUnit)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .build();

    }

    public Category get(String key){
        return categoryCache.getIfPresent(key);

    }
    public void evict(String key){
        categoryCache.invalidate(key);
    }

    public void add(String key, Category value){
        categoryCache.put(key, value);
    }

}