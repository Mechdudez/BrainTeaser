package com.kenzie.appserver.config;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kenzie.appserver.repositories.model.CategoryRecord;


import java.util.List;

public class CachingQuestions {
    private final LoadingCache<String, List<CategoryRecord>> categoryCache;


    public CachingQuestions(QuestionStorageDAO categoryDao) {
        categoryCache = CacheBuilder.newBuilder()
                .build(CacheLoader.from(categoryDao::getQuestions));
    }


    public List<CategoryRecord> getQuestions(String questionId) {

        return categoryCache.getUnchecked(questionId);
    }
}


