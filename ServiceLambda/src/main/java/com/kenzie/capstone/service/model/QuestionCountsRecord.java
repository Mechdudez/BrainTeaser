package com.kenzie.capstone.service.model;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

@DynamoDBTable(tableName = "QuestionCountsLambda")

public class QuestionCountsRecord {
    private Integer questionId;
    private Integer countsPicked;


    @DynamoDBHashKey(attributeName = "QuestionId")
    public Integer getQuestionId() {
        return questionId;
    }


    @DynamoDBAttribute(attributeName = "PickedCounts")
    public Integer getPickedCounts() {
        return countsPicked;
    }


    public void setPickedCounts(Integer countsPicked) {
        this.countsPicked = countsPicked;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QuestionCountsRecord that = (QuestionCountsRecord) o;
        return Objects.equals(questionId, that.questionId) && Objects.equals(countsPicked,
                that.countsPicked);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId, countsPicked);
    }
}
