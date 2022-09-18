package com.kenzie.capstone.service.model;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

@DynamoDBTable(tableName = "UserAnswerLambda")

public class UserAnswerRecord {
    private String userId;
    private String questionId;
    private String answerKey;
    private String userAnswer;


    @DynamoDBHashKey(attributeName = "UserId")
    public String getUserId() {
        return userId;
    }


    @DynamoDBAttribute(attributeName = "QuestionId")
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "QuestionIdIndex", attributeName = "QuestionId")
    public String getQuestionId() {
        return questionId;
    }

    @DynamoDBAttribute(attributeName = "AnswerKey")
    public String getAnswerKey() {
        return answerKey;
    }

    @DynamoDBAttribute(attributeName = "UserAnswer")
    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserId(String userid) {
        this.userId = userId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public void setAnswerKey(String answerKey) {
        this.answerKey= answerKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserAnswerRecord that = (UserAnswerRecord) o;
        return Objects.equals(userId, that.userId) && Objects.equals(questionId,
                that.questionId) && Objects.equals(userAnswer,
                that.userAnswer) && Objects.equals(answerKey,
                that.answerKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, questionId, userAnswer, answerKey);
    }
}
