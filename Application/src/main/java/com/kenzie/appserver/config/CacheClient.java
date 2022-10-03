package com.kenzie.appserver.config;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.kenzie.appserver.service.model.Category;

import java.util.concurrent.TimeUnit;

public class CacheClient {

    private Cache<Integer, Category> categoryCache;

    // Help from Dan Sun
    public CacheClient(int expire, TimeUnit timeUnit) {
        // initalize the cache
        this.categoryCache = CacheBuilder.newBuilder()
                .expireAfterWrite(expire, timeUnit)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .build();
    }

    public Category get(Integer key){
        return categoryCache.getIfPresent(key);

    }
    public void evict(Integer key){
        categoryCache.invalidate(key);
    }

    public void add(Integer key, Category value){
        categoryCache.put(key, value);
    }

}
