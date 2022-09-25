package com.kenzie.capstone.service.model;

public class QuestionCountsRequest {
    private String questionId;


    public QuestionCountsRequest(String questionId){
        this.questionId = questionId;
    }

    public QuestionCountsRequest(){}

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }


    @Override
    public String toString() {
        return "UserAnswerRequest{" +
                ", questionId='" + questionId + '\'' +
                '}';
    }

}
