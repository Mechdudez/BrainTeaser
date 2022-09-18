package com.kenzie.capstone.service.dependency;

import com.kenzie.capstone.service.CheckUserAnswerService;
import com.kenzie.capstone.service.LambdaService;
import com.kenzie.capstone.service.dao.ExampleDao;
import com.kenzie.capstone.service.dao.UserAnswerDao;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Module(
        includes = UserAnswerDaoModule.class
)

public class UserAnswerServiceModule {
    @Singleton
    @Provides
    @Inject
    public CheckUserAnswerService provideLambdaService(@Named(
            "UserAnswerDao") UserAnswerDao userAnswerDao) {
        return new CheckUserAnswerService(userAnswerDao);
    }
}
