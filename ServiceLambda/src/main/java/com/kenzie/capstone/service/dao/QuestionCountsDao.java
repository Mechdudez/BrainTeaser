package com.kenzie.capstone.service.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.google.common.collect.ImmutableMap;
import com.kenzie.capstone.service.model.QuestionCountsRecord;
import com.kenzie.capstone.service.model.QuestionCountsRequest;

import java.util.List;

public class QuestionCountsDao {
    private DynamoDBMapper mapper;

    /**
     * Allows access to and manipulation of Match objects from the data store.
     * @param mapper Access to DynamoDB
     */
    public QuestionCountsDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    public QuestionCountsRecord storeQuestionCountsData(QuestionCountsRecord questionCountsRecord) {
        try {
            mapper.save(questionCountsRecord, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of(
                            "QuestionId",
                            new ExpectedAttributeValue().withExists(false)
                    )));
        } catch (ConditionalCheckFailedException e) {
            throw new IllegalArgumentException("id has already been used");
        }

        return questionCountsRecord;
    }

}
