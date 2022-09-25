package com.kenzie.capstone.service.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kenzie.capstone.service.CheckQuestionCountsService;
import com.kenzie.capstone.service.dependency.ServiceComponent;
import com.kenzie.capstone.service.dependency.DaggerServiceComponent;
import com.kenzie.capstone.service.model.QuestionCountsRequest;
import com.kenzie.capstone.service.model.QuestionCountsResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class AddQuestionCounts implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    static final Logger log = LogManager.getLogger();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        log.info(gson.toJson(input));

        ServiceComponent checkQuestionCountsServiceComponent =
                DaggerServiceComponent.create();
        CheckQuestionCountsService checkQuestionCountsService =
                checkQuestionCountsServiceComponent.provideQuestionCountsLambdaService();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);

        String userId = input.getPathParameters().get("UserId");

        if(userId == null || userId.isEmpty()){
            return response.withStatusCode(400).withBody("No UserId" +
                    " found!");

        }

        try {
            QuestionCountsRequest questionCountsRequest =
                    jsonStringToUserAnswerConverter(input.getBody());
            QuestionCountsResponse questionCountsResponse =
                    checkQuestionCountsService.addQuestion(questionCountsRequest);
            return response
                    .withStatusCode(200)
                    .withBody(gson.toJson(questionCountsResponse));
        } catch (RuntimeException e) {
            return response
                    .withStatusCode(400)
                    .withBody(gson.toJson(e.getMessage()));
        }
    }

    public QuestionCountsRequest jsonStringToUserAnswerConverter(String body) {
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            QuestionCountsRequest questionCountsRequest =
                    gson.fromJson(body, QuestionCountsRequest.class);
            return questionCountsRequest;
        } catch (Exception e) {
            throw new RuntimeException("UserAnswer could not be" +
                    " deserialized");
        }
    }
}
