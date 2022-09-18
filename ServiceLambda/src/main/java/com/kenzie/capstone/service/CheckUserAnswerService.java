package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.ExampleDao;
import com.kenzie.capstone.service.dao.UserAnswerDao;
import com.kenzie.capstone.service.model.*;

import javax.inject.Inject;
import java.util.UUID;

public class CheckUserAnswerService {
    private UserAnswerDao userAnswerDao;

    @Inject
    public CheckUserAnswerService(UserAnswerDao userAnswerDao) {
        this.userAnswerDao = userAnswerDao;
    }

    public UserAnswerResponse addUserAnswer(UserAnswerRequest userAnswerRequest) {
        if (userAnswerRequest == null || userAnswerRequest.getUserId() == null || userAnswerRequest.getUserId().length() == 0) {
            throw new RuntimeException("Request must contain a valid Customer ID");
        }


        // need to call other service to get the answerkey
        // current implementation is a placeholder and is wrong

        userAnswerDao.setUserAnswerRecord(userAnswerRequest.getUserId(),
                                          userAnswerRequest.getQuestionId(),
                userAnswerRequest.getAnswerKey(), userAnswerRequest.getUserAnswer()

                 );

        UserAnswerResponse userAnswerResponse =
                new UserAnswerResponse();

        userAnswerResponse.setUserId(userAnswerRequest.getUserId());
        userAnswerResponse.setQuestionId(userAnswerRequest.getQuestionId());
        userAnswerResponse.setUserAnswer(userAnswerRequest.getUserAnswer());
        userAnswerResponse.setResult(userAnswerRequest.getResult());

        return userAnswerResponse;
    }
}
