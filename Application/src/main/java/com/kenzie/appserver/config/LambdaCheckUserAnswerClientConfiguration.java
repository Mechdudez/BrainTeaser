package com.kenzie.appserver.config;

import com.kenzie.capstone.service.client.CheckQuestionCountsServiceClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LambdaCheckUserAnswerClientConfiguration {

    @Bean
    public CheckQuestionCountsServiceClient checkQuestionCountsServiceClient() {
        return new CheckQuestionCountsServiceClient();
    }
}

