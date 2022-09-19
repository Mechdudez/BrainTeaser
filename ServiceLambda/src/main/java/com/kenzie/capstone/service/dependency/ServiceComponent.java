package com.kenzie.capstone.service.dependency;

import com.kenzie.capstone.service.CheckUserAnswerService;
import com.kenzie.capstone.service.LambdaService;

import dagger.Component;

import javax.inject.Singleton;

/**
 * Declares the dependency roots that Dagger will provide.
 */
@Singleton
@Component(modules = {UserAnswerDaoModule.class, UserAnswerServiceModule.class})
public interface ServiceComponent {
    CheckUserAnswerService provideUserAnswerLambdaService();

}
