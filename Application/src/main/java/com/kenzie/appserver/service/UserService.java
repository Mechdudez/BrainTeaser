package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.UserCreateRequest;
import com.kenzie.appserver.repositories.CategoryRepository;
import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.CategoryRecord;
import com.kenzie.appserver.repositories.model.UserRecord;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

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

    }


}
