package com.kenzie.capstone.service.model;

// package me.xdrop.fuzzywuzzy;

//import FuzzyWuzzy
// not workig yest
//import me.xdrop.fuzzywuzzy.FuzzySearch;

public class QuestionCountsResponse {

    private String questionId;
    private Integer countsPicked;

    public QuestionCountsResponse(String questionId,
                                Integer countsPicked
    ) {
        this.questionId = questionId;
        this.countsPicked = countsPicked;
    }


    public UserAnswerResponse(){}

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public Integer getQuestionCounts() {
        return countsPicked;
    }

    public void setQuestionCounts(Integer countsPicked) {
        this.countsPicked = countsPicked;
    }
    @Override
    public String toString() {
        return "UserAnswerResponse{" +
                ", questionId='" + questionId + '\'' +
                ", questionCounts='" + countsPicked + '\'' +
                '}';
    }
}
