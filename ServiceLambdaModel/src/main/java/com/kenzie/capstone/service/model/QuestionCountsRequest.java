package com.kenzie.capstone.service.model;

public class QuestionCountsRequest {
    private Integer questionId;


    public QuestionCountsRequest(Integer questionId){
        this.questionId = questionId;
    }

    public QuestionCountsRequest(){}

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }


    @Override
    public String toString() {
        return "UserAnswerRequest{" +
                ", questionId='" + questionId + '\'' +
                '}';
    }

}
