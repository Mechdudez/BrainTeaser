package com.kenzie.capstone.service.model;

public class UserAnswerRequest {
    private String userId;
    private String questionId;

    private String userAnswer;

    private String answerKey;

    public UserAnswerRequest(String userId, String questionId,
                             String userAnswer, String answerKey){
        this.userId = userId;
        this.questionId = questionId;
        this.userAnswer = userAnswer;
        this.answerKey = answerKey;
    }

    public UserAnswerRequest(){}

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

    public String getAnswerKey() {
        return answerKey;
    }

    public void setAnswerKey(String answerKey) {
        this.answerKey = answerKey;
    }

    @Override
    public String toString() {
        return "UserAnswerRequest{" +
                "userId='" + userId + '\'' +
                ", questionId='" + questionId + '\'' +
                ", userAnswer='" + userAnswer + '\'' +
                ", answerKey='" + answerKey + '\'' +
                '}';
    }

}
