package com.kenzie.capstone.service.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.capstone.service.model.QuestionCountsRequest;
import com.kenzie.capstone.service.model.QuestionCountsResponse;

public class CheckQuestionCountsServiceClient {
    private static final String ADD_USER_ANSWER_ENDPOINT =
            "useranswer/add";
    private static final String GET_USER_ANSWER_SUMMARY_ENDPOINT =
            "useranswer/summary/{userId}";
    private static final String GET_USER_ANSWER_RESULT_ENDPOINT =
            "useranswer/result/{userId}";

    private ObjectMapper mapper;

    public CheckQuestionCountsServiceClient() {
        this.mapper = new ObjectMapper();
    }

//    public UserAnswerResponse addUserAnswer(UserAnswerRequest userAnswerRequest) {
//        EndpointUtility endpointUtility = new EndpointUtility();
//        String request;
//        try {
//            request = mapper.writeValueAsString(userAnswerRequest);
//        } catch(JsonProcessingException e) {
//            throw new ApiGatewayException("Unable to serialize request: " + e);
//        }
//        String response = endpointUtility.postEndpoint(ADD_USER_ANSWER_ENDPOINT, request);
//        UserAnswerResponse userAnswerResponse;
//        try {
//            userAnswerResponse = mapper.readValue(response, UserAnswerResponse.class);
//        } catch (Exception e) {
//            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
//        }
//        return userAnswerResponse;
//    }

    public QuestionCountsResponse countQuestionsChosen(QuestionCountsRequest questionIdRequest){
        EndpointUtility endpointUtility = new EndpointUtility();
        String request;
        try {
            request = mapper.writeValueAsString(questionIdRequest);
        } catch(JsonProcessingException e) {
            throw new ApiGatewayException("Unable to serialize request: " + e);
        }
        String response = endpointUtility.postEndpoint(ADD_USER_ANSWER_ENDPOINT, request);
        QuestionCountsResponse questionCountsResponse;
        try {
            questionCountsResponse = mapper.readValue(response,QuestionCountsResponse.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return questionCountsResponse;

    }
}
