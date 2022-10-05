package com.kenzie.capstone.service.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.capstone.service.model.QuestionCountsRequest;
import com.kenzie.capstone.service.model.QuestionCountsResponse;
import com.fasterxml.jackson.core.type.TypeReference;

public class CheckQuestionCountsServiceClient {
    private static final String ADD_QUESTION_COUNTS_ENDPOINT =
            "questioncounts/";
    private static final String GET_QUESTION_FREQ_ENDPOINT =
            "questioncounts/{questionId}";

    private ObjectMapper mapper;

    public CheckQuestionCountsServiceClient() {
        this.mapper = new ObjectMapper();
    }


    public QuestionCountsResponse countQuestionsChosen(QuestionCountsRequest questionIdRequest){
        EndpointUtility endpointUtility = new EndpointUtility();
        String request;
        try {
            request = mapper.writeValueAsString(questionIdRequest);
        } catch(JsonProcessingException e) {
            throw new ApiGatewayException("Unable to serialize request: " + e);
        }
        String response = endpointUtility.postEndpoint(ADD_QUESTION_COUNTS_ENDPOINT, request);
        QuestionCountsResponse questionCountsResponse;
        try {
            questionCountsResponse = mapper.readValue(response,QuestionCountsResponse.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return questionCountsResponse;

    }

    public Integer getQuestionFreq(Integer questionId) {
        EndpointUtility endpointUtility = new EndpointUtility();
        String response =
                endpointUtility.getEndpoint(GET_QUESTION_FREQ_ENDPOINT.replace("{questionId}", questionId.toString()));
        QuestionCountsResponse questionCountsResponse;
        try {
            questionCountsResponse = mapper.readValue(response, new TypeReference<>(){});
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return questionCountsResponse.getCountsPicked();
    }
}
