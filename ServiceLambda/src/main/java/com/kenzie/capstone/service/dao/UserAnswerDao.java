package com.kenzie.capstone.service.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.google.common.collect.ImmutableMap;
import com.kenzie.capstone.service.model.ExampleData;
import com.kenzie.capstone.service.model.ExampleRecord;
import com.kenzie.capstone.service.model.UserAnswerData;
import com.kenzie.capstone.service.model.UserAnswerRecord;

import java.util.List;

public class UserAnswerDao {
    private DynamoDBMapper mapper;

    /**
     * Allows access to and manipulation of Match objects from the data store.
     * @param mapper Access to DynamoDB
     */
    public UserAnswerDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    public UserAnswerData storeUserAnswerData(UserAnswerData userAnswerData) {
        try {
            mapper.save(userAnswerData, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of(
                            "UserId",
                            new ExpectedAttributeValue().withExists(false)
                    )));
        } catch (ConditionalCheckFailedException e) {
            throw new IllegalArgumentException("id has already been used");
        }

        return userAnswerData;
    }

    public List<UserAnswerRecord> getUserAnswerRecord(String userId
            , String questionId) {
        UserAnswerRecord userAnswerRecord = new UserAnswerRecord();
        userAnswerRecord.setUserId(userId);
        userAnswerRecord.setQuestionId(questionId);

        DynamoDBQueryExpression<UserAnswerRecord> queryExpression =
                new DynamoDBQueryExpression<UserAnswerRecord>()
                        .withHashKeyValues(userAnswerRecord)
                        .withIndexName("QuestionId")
                        .withConsistentRead(false);

        return mapper.query(UserAnswerRecord.class, queryExpression);
    }

    public UserAnswerRecord setUserAnswerRecord(String userId,
                                              String questionId,
                                                String result,
                                                String userAnswer) {
        UserAnswerRecord userAnswerRecord = new UserAnswerRecord();
        userAnswerRecord.setUserId(userId);
        userAnswerRecord.setQuestionId(questionId);
        userAnswerRecord.setUserAnswer(userAnswer);
        userAnswerRecord.setResult(result);

        try {
            mapper.save(userAnswerRecord, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of(
                            "UserId",
                            new ExpectedAttributeValue().withExists(false)
                    )));
        } catch (ConditionalCheckFailedException e) {
            throw new IllegalArgumentException("id already exists");
        }

        return userAnswerRecord;
    }
}
