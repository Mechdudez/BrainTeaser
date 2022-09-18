package com.kenzie.capstone.service.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kenzie.capstone.service.CheckUserAnswerService;
import com.kenzie.capstone.service.dependency.DaggerServiceComponent;
import com.kenzie.capstone.service.dependency.ServiceComponent;
import com.kenzie.capstone.service.model.UserAnswerRequest;
import com.kenzie.capstone.service.model.UserAnswerResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class AddUserAnswer implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    static final Logger log = LogManager.getLogger();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        log.info(gson.toJson(input));

        ServiceComponent userAnswerServiceComponent =
                DaggerServiceComponent.create();
        CheckUserAnswerService checkUserAnswerService =
                userAnswerServiceComponent.provideLambdaService();
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
            UserAnswerRequest userAnswerRequest =
                    jsonStringToUserAnswerConverter(input.getBody());
            UserAnswerResponse userAnswerResponse =
                    checkUserAnswerService.addUserAnswer(userAnswerRequest);
            return response
                    .withStatusCode(200)
                    .withBody(gson.toJson(userAnswerResponse));
        } catch (RuntimeException e) {
            return response
                    .withStatusCode(400)
                    .withBody(gson.toJson(e.getMessage()));
        }
    }

    public UserAnswerRequest jsonStringToUserAnswerConverter(String body) {
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            UserAnswerRequest userAnswerRequest =
                    gson.fromJson(body, UserAnswerRequest.class);
            return userAnswerRequest;
        } catch (Exception e) {
            throw new RuntimeException("UserAnswer could not be" +
                    " deserialized");
        }
    }
}
