package com.kenzie.appserver.config;

import com.kenzie.appserver.repositories.model.CategoryRecord;

public class CachingQuestion implements QuestionDao {

    private  static final String CATEGORY_KEY = "CategoryKey::%s";

    private CacheClient cacheClient;

    private NonCachingQuestion nonCachingReferralDao;

    public CachingQuestion(CacheClient cacheClient, NonCachingQuestion nonCachingReferralDao) {
        this.cacheClient = cacheClient;
        this.nonCachingReferralDao = nonCachingReferralDao;
    }
    @Override
    public CategoryRecord addQuestion(CategoryRecord category) {

        //
        if (category.getQuestionId() == null) {
            String question = String.format(CATEGORY_KEY, category.getQuestionId());
            cacheClient.invalidate(question);
        }
        // Add referral to database
        return nonCachingReferralDao.addQuestion(category);
    }

}
