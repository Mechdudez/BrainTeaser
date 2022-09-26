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
import com.kenzie.appserver.service.model.Category;
import com.kenzie.appserver.service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(UUID userId) {

        User user = userRepository.findById(userId)
                .map(user1 -> new User(user1.getUsername(), user1.getUserId(), user1.getPoints()))
                .orElse(null);

        if (user == null) {
            throw new UserNotFoundException("No user found by id!");
        }


        return user;
    }


    public User addNewUser(User user) {
        if (user == null) {
            throw new UserNotFoundException("Sorry this user was not found");
        }
        UserRecord userRecord = new UserRecord();
        userRecord.setUserId(user.getUserId());
        userRecord.setUsername(user.getUserName());
        userRecord.setPoints(user.getPoints());

        userRepository.save(userRecord);
        return user;

    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        // iterate through each question and add them to a list.
        List<UserRecord> recordList = new ArrayList<>();
        userRepository.findAll()
                .forEach(recordList::add);
        // add the list into a new category record
        for (UserRecord userRecord : recordList) {
            userList.add(new User(userRecord.getUsername(), userRecord.getUserId(), userRecord.getPoints()));
        }

        return userList; // return that list.
    }


    public List<User> getTopScores() {
        List<User> users = getAllUsers();

        users.sort(Comparator.comparing(User::getPoints).reversed());

        return users.stream().limit(5).collect(Collectors.toList());

    }


    public UserRecord getUserWithTopScore() {
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

        for (User user : scanResult) {
            sortedPoints.put(user.getPoints(), user.getUserName());
        }

        Set<Entry<Integer, String>> mappings =
                sortedPoints.entrySet();
        List<UserRecord> sortedRecordByPoints = new ArrayList<>();
        for (Entry<Integer, String> mapping : mappings) {
            UserRecord topPointsUserRecord = new UserRecord();
            topPointsUserRecord.setPoints(mapping.getKey());
            topPointsUserRecord.setUsername(mapping.getValue());
            sortedRecordByPoints.add(topPointsUserRecord);
        }

        return sortedRecordByPoints.get(0);
    }


}
