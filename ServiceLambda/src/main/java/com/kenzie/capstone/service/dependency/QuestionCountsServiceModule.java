package com.kenzie.capstone.service.dependency;

import com.kenzie.capstone.service.CheckQuestionCountsService;
import com.kenzie.capstone.service.dao.QuestionCountsDao;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Module(
        includes = QuestionCountsDaoModule.class
)

public class QuestionCountsServiceModule {
    @Singleton
    @Provides
    @Inject
    public CheckQuestionCountsService provideQuestionCountsLambdaService(@Named(
            "QuestionCountsDao") QuestionCountsDao questionCountsDao) {
        return new CheckQuestionCountsService(questionCountsDao);
    }
}
