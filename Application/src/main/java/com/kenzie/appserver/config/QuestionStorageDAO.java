package com.kenzie.appserver.config;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.kenzie.appserver.repositories.model.CategoryRecord;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionStorageDAO {

    private final DynamoDBMapper mapper;


    public QuestionStorageDAO(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }


    public List<CategoryRecord> getQuestions(String questionId) {
        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":questionId", new AttributeValue().withS(questionId));
        DynamoDBQueryExpression<CategoryRecord> queryExpression = new DynamoDBQueryExpression<CategoryRecord>()
                .withKeyConditionExpression("question_Id = :questionId")
                .withExpressionAttributeValues(valueMap);

        System.out.println("Getting orders from DynamoDB for user " + questionId);

        QueryResultPage<CategoryRecord> queryList = mapper.queryPage(CategoryRecord.class, queryExpression);
        List<CategoryRecord> categories = queryList.getResults();
        if (categories == null) {
            return new ArrayList<CategoryRecord>();
        }
        return categories;
    }
}
