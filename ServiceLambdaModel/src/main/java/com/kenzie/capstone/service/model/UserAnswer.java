package com.kenzie.capstone.service.model;

import java.util.Date;
import java.util.Objects;

public class UserAnswer {
    private String userId;
    private String questionId;
    private String userAnswer;
    private String answerKey;
    private Date answerDate;

    public UserAnswer(String userId, String questionId,
                      String userAnswer, String answerKey, Date answerDate){
        this.userId = userId;
        this.questionId = questionId;
        this.userAnswer = userAnswer;
        this.answerKey = answerKey;
        this.answerDate = answerDate;
    }

    public UserAnswer() {}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
