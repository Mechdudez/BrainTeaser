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

    public Integer getQuestionFreq(Integer questionId){
        List<QuestionCountsRecord> records =
                questionCountsDao.getQuestionCounts(questionId);

        if (records.size() > 0) {
            System.out.println("Debugging question id for picking " +
                    "counts is " + records.get(0).getQuestionId());
            return records.get(0).getPickedCounts();
        }
        return null;

    }

    public QuestionCountsResponse addQuestion(QuestionCountsRequest questionIdRequest) {

        if (questionIdRequest == null) {
            throw new IllegalArgumentException("Request must " +
                    "contain a valid question ID");
        }

        // Will need to keep track if the questionId has been used or not.
        // if it hasn't been used add that question in the map and set value to 1
        // if it has been used then just increase value by 1
        // check if recordId is in the database


        QuestionCountsRecord record = new QuestionCountsRecord();
        record.setQuestionId(questionIdRequest.getQuestionId());
        // need to increment here if new record
//        HashMap<Integer, Integer> map = new HashMap<>();
//        Integer key = record.getQuestionId();
//        Integer value = 0;
//        if(!map.containsKey(key)){
//            value = 1;
//            map.put(key, value);
//        }else {
////            value += value;
////            map.put(key, value);
//            map.put(key, map.get(key) + 1);
//        }

        questionCountsDao.storeQuestionCountsData(record);

        QuestionCountsResponse response =
                new QuestionCountsResponse();
        response.setQuestionId(record.getQuestionId());
        response.setCountsPicked(record.getPickedCounts());
        return response;
    }
}
