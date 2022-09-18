package com.kenzie.capstone.service.client;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CheckAnswerServiceClient {
    private static final String ADD_USER_ANSWER_ENDPOINT =
            "useranswer/add";
    private static final String GET_USER_ANSWER_SUMMARY_ENDPOINT =
            "useranswer/summary";
    private static final String GET_USER_ANSWER_RESULT_ENDPOINT =
            "useranswer/result";

    private ObjectMapper mapper;

    public CheckAnswerServiceClient() {
        this.mapper = new ObjectMapper();
    }
}
