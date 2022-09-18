package com.kenzie.capstone.service.model;

// package me.xdrop.fuzzywuzzy;

//import FuzzyWuzzy
// not workig yest
//import me.xdrop.fuzzywuzzy.FuzzySearch;

public class UserAnswerResponse {
    private String userId;
    private String questionId;

    private String userAnswer;

    private String answerKey;

    private Boolean result;

    public UserAnswerResponse(String userId, String questionId,
                             String userAnswer, Boolean result){
        this.userId = userId;
        this.questionId = questionId;
        this.userAnswer = userAnswer;
        this.result = result;
    }

    public UserAnswerResponse(){}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public Boolean getResult() {
        // change to this later
//        int matchingScore = FuzzySearch.ratio("mysmilarstring",
//                "mysimilarstring");
//        if(matchingScore >= 90){
//            return Boolean.TRUE;
//        }
//        return Boolean.FALSE;
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "UserAnswerRequest{" +
                "userId='" + userId + '\'' +
                ", questionId='" + questionId + '\'' +
                ", userAnswer='" + userAnswer + '\'' +
                ", answerKey='" + answerKey + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
