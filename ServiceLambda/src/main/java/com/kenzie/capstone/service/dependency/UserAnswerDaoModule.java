package com.kenzie.capstone.service.dependency;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.kenzie.capstone.service.dao.ExampleDao;
import com.kenzie.capstone.service.dao.UserAnswerDao;
import com.kenzie.capstone.service.util.DynamoDbClientProvider;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class UserAnswerDaoModule {
    @Singleton
    @Provides
    @Named("DynamoDBMapper")
    public DynamoDBMapper provideDynamoDBMapper() {
        return new DynamoDBMapper(DynamoDbClientProvider.getDynamoDBClient());
    }

    @Singleton
    @Provides
    @Named("UserAnswerDao")
    @Inject
    public UserAnswerDao provideUserAnswerDao(@Named(
            "DynamoDBMapper") DynamoDBMapper mapper) {
        return new UserAnswerDao(mapper);
    }
}
