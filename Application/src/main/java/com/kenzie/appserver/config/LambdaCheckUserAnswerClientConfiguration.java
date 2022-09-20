package com.kenzie.appserver.config;

import com.kenzie.capstone.service.client.CheckAnswerServiceClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LambdaCheckUserAnswerClientConfiguration {

    @Bean
    public CheckAnswerServiceClient checkUserAnswerClient() {
        return new CheckAnswerServiceClient();
    }
}

