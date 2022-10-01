package com.kenzie.appserver.config;

import com.github.benmanes.caffeine.cache.LoadingCache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.kenzie.appserver.repositories.model.CategoryRecord;
import com.kenzie.appserver.service.model.Category;

import java.util.List;

public class CachingQuestion {
    private final LoadingCache<String, List<CategoryRecord>> categoryCache;


    public CachingOnlineOrdersDAO(QuestionStorageDAO ordersCache) {
        categoryCache = CacheBuilder.newBuilder()
                .build(CacheLoader.from(QuestionStorageDAO::getQuestions));
    }


    public List<Category> getOrdersByUser(String userId) {

        return categoryCache.getUnchecked(userId);
    }
}


