package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.QuestionCountsDao;
import com.kenzie.capstone.service.model.QuestionCountsRecord;
import com.kenzie.capstone.service.model.QuestionCountsRequest;
import com.kenzie.capstone.service.model.QuestionCountsResponse;

import javax.inject.Inject;

public class CheckQuestionCountsService {
    private QuestionCountsDao questionCountsDao;

    @Inject
    public CheckQuestionCountsService(QuestionCountsDao questionCountsDao) {
        this.questionCountsDao = questionCountsDao;
    }

    public QuestionCountsResponse addQuestion(QuestionCountsRequest questionIdRequest) {
        if (questionIdRequest == null) {
            throw new IllegalArgumentException("Request must " +
                    "contain a valid question ID");
        }

        // check if recordId is in the database


        QuestionCountsRecord record = new QuestionCountsRecord();
        record.setQuestionId(questionIdRequest.getQuestionId());
        // need to increment here if new record
        record.setPickedCounts(1);

        questionCountsDao.storeQuestionCountsData(record);

        QuestionCountsResponse response =
                new QuestionCountsResponse();
        response.setQuestionId(record.getQuestionId());
        response.setCountsPicked(record.getPickedCounts());
        return response;
    }
}
