package com.kenzie.appserver.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.kenzie.appserver.controller.model.UserCreateRequest;
import com.kenzie.appserver.repositories.CategoryRepository;
import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.CategoryRecord;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.Map.Entry;

@Service
public class UserService {
    private UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserRecord getUserById(String userId) {
        // getting data from the local repository
        Optional<UserRecord> userRecord =
                userRepository.findById(userId);

        if(!userRecord.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "User record Not Found");
        }

        return userRecord.get();
    }

    public UserRecord addNewUser(UserCreateRequest userCreateRequest){
        UserRecord userRecord = new UserRecord();
        userRecord.setUserId(UUID.randomUUID().toString());
        userRecord.setUsername(userCreateRequest.getUserName());
        userRecord.setPoints(0);

        userRepository.save(userRecord);
        return userRecord;

    }

    public UserRecord getUserWithTopScore(){
        // Need to think hard on this
        DynamoDBMapperConfig mapperConfig = new DynamoDBMapperConfig.Builder()
                .withTableNameOverride(DynamoDBMapperConfig.TableNameOverride.withTableNameReplacement("user"))
                .build();
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        DynamoDBMapper mapper = new DynamoDBMapper(client, mapperConfig);
        List<User> scanResult = mapper.scan(User.class,
                scanExpression);


        TreeMap<Integer, String> sortedPoints =
                new TreeMap<>(Collections.reverseOrder());

        for(User user:scanResult){
            sortedPoints.put(user.getPoints(), user.getUserName());
        }

        Set<Entry<Integer, String>> mappings =
                sortedPoints.entrySet();
        List<UserRecord> sortedRecordByPoints = new ArrayList<>();
        for(Entry<Integer, String> mapping : mappings) {
            UserRecord topPointsUserRecord = new UserRecord();
            topPointsUserRecord.setPoints(mapping.getKey());
            topPointsUserRecord.setUsername(mapping.getValue());
            sortedRecordByPoints.add(topPointsUserRecord);
        }

      return sortedRecordByPoints.get(0);
    }


}
