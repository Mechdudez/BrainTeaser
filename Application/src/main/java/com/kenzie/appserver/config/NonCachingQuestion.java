package com.kenzie.appserver.config;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.google.common.collect.ImmutableMap;
import com.kenzie.appserver.repositories.model.CategoryRecord;
import com.kenzie.appserver.service.CategoryNotFoundException;

public class NonCachingQuestion {

private DynamoDBMapper mapper;


    public NonCachingQuestion(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    public CategoryRecord addQuestion(CategoryRecord category){
        try {
            mapper.save(category, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of(
                            "QuestionId",
                            new ExpectedAttributeValue().withExists(false)
                    )));
        } catch (ConditionalCheckFailedException e) {
            throw new CategoryNotFoundException("Question has already been added");
        }

        return category;
    }

    }

