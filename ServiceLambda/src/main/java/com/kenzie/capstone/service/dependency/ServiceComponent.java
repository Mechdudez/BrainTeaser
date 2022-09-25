package com.kenzie.capstone.service.dependency;

import com.kenzie.capstone.service.CheckQuestionCountsService;

import dagger.Component;

import javax.inject.Singleton;

/**
 * Declares the dependency roots that Dagger will provide.
 */
@Singleton
@Component(modules = {QuestionCountsDaoModule.class, QuestionCountsServiceModule.class})
public interface ServiceComponent {
    CheckQuestionCountsService provideQuestionCountsLambdaService();

}
