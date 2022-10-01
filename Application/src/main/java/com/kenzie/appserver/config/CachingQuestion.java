package com.kenzie.appserver.config;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kenzie.appserver.repositories.model.CategoryRecord;


import java.util.List;

public class CachingQuestion {
    private final LoadingCache<String, List<CategoryRecord>> categoryCache;


    public CachingQuestion(QuestionStorageDAO categoryDao) {
        categoryCache = CacheBuilder.newBuilder()
                .build(CacheLoader.from(categoryDao::getQuestions));
    }


    public List<CategoryRecord> getQuestion(String questionId) {

        return categoryCache.getUnchecked(questionId);
    }
}


