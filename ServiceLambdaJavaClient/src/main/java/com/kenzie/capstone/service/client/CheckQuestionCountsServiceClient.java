package com.kenzie.capstone.service.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.capstone.service.model.QuestionCountsRequest;
import com.kenzie.capstone.service.model.QuestionCountsResponse;

public class CheckQuestionCountsServiceClient {
    private static final String ADD_QUESTION_COUNTS_ENDPOINT =
            "questioncounts/";
    private static final String GET_ANSWER_KEY_ENDPOINT =
            "questioncounts/{questionId}";

    private ObjectMapper mapper;

    public CheckQuestionCountsServiceClient() {
        this.mapper = new ObjectMapper();
    }


    public QuestionCountsResponse countQuestionsChosen(QuestionCountsRequest questionIdRequest){
        EndpointUtility endpointUtility = new EndpointUtility();
        System.out.println("debugging lambda" + questionIdRequest);
        String request;
        try {
            request = mapper.writeValueAsString(questionIdRequest);
        } catch(JsonProcessingException e) {
            throw new ApiGatewayException("Unable to serialize request: " + e);
        }
        System.out.println("debugging request string format" + request);
        String response = endpointUtility.postEndpoint(ADD_QUESTION_COUNTS_ENDPOINT, request);
        QuestionCountsResponse questionCountsResponse;
        System.out.println("debugging question Response" + response);
        try {
            questionCountsResponse = mapper.readValue(response,QuestionCountsResponse.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return questionCountsResponse;

    }
}
