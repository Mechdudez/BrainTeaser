package com.kenzie.capstone.service.dependency;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.kenzie.capstone.service.dao.QuestionCountsDao;
import com.kenzie.capstone.service.util.DynamoDbClientProvider;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class QuestionCountsDaoModule {
    @Singleton
    @Provides
    @Named("DynamoDBMapper")
    public DynamoDBMapper provideDynamoDBMapper() {
        return new DynamoDBMapper(DynamoDbClientProvider.getDynamoDBClient());
    }

    @Singleton
    @Provides
    @Named("QuestionCountsDao")
    @Inject
    public QuestionCountsDao provideQuestionCountsDao(@Named(
            "DynamoDBMapper") DynamoDBMapper mapper) {
        return new QuestionCountsDao(mapper);
    }
}
