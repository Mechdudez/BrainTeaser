package com.kenzie.capstone.service.model;
// package me.xdrop.fuzzywuzzy;

//import FuzzyWuzzy
// not workig yest
//import me.xdrop.fuzzywuzzy.FuzzySearch;

import java.util.Date;
import java.util.Objects;

public class UserAnswer {
    private String userId;
    private String questionId;
    private String userAnswer;
    private String answerKey;
    private Date answerDate;

    private Boolean result;

    public UserAnswer(String userId, String questionId,
                      String userAnswer, String answerKey,
                      Date answerDate, Boolean result){
        this.userId = userId;
        this.questionId = questionId;
        this.userAnswer = userAnswer;
        this.answerKey = answerKey;
        this.answerDate = answerDate;
        this.result = result;
    }

    public UserAnswer() {}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionrId(String questionId) {
        this.questionId = questionId;
    }

    public String getUserAnswer(String userAnswer){
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer){
        this.userAnswer = userAnswer;
    }

    public String getAnswerKey(String answerKey){
        return answerKey;
    }

    public void setAnswerKey(String answerKey){
        this.answerKey = answerKey;
    }

    public Date getAnswerDate(Date answerDate){
        return answerDate;
    }

    public void setAnswerDate(Date answerDate){
        this.answerDate = answerDate;
    }

    public Boolean getResult(Boolean result){

        return result;
    }

    public void setResult(Boolean result){
        this.result = result;

    }



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserAnswer userAnswer = (UserAnswer) o;
        return Objects.equals(userId, userAnswer.userId) &&
                Objects.equals(questionId, userAnswer.questionId) &&
                Objects.equals(userAnswer, userAnswer.userAnswer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, questionId, userAnswer);
    }

    @Override
    public String toString() {
        return "UserAnswer{" +
                "userId='" + userId + '\'' +
                ", questionId='" + questionId + '\'' +
                ", userAnswer='" + userAnswer + '\'' +
                '}';
    }

}
