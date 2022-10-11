package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.QuestionCountsDao;
import com.kenzie.capstone.service.model.*;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;

public class CheckQuestionCountsService {
    private QuestionCountsDao questionCountsDao;

    @Inject
    public CheckQuestionCountsService(QuestionCountsDao questionCountsDao) {
        this.questionCountsDao = questionCountsDao;
    }
    // TODO work in progress
//    public Integer getQuestionFreq(Integer questionId){
//        List<QuestionCountsRecord> records =
//                questionCountsDao.getQuestionCounts(questionId);
//
//        if (records.size() > 0) {
//            System.out.println("Debugging question id for picking " +
//                    "counts is " + records.get(0).getQuestionId());
//            return records.get(0).getPickedCounts();
//        }
//        return null;
//
//    }

    public QuestionCountsResponse addQuestion(QuestionCountsRequest questionIdRequest) {

        QuestionCountsRecord record = new QuestionCountsRecord();
        record.setQuestionId(questionIdRequest.getQuestionId());

        questionCountsDao.storeQuestionCountsData(record);

        QuestionCountsResponse response =
                new QuestionCountsResponse();
        response.setQuestionId(record.getQuestionId());
        response.setCountsPicked(record.getPickedCounts());
        return response;
    }
}
