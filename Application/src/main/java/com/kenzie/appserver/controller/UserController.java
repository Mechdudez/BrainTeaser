package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.CategoryResponse;
import com.kenzie.appserver.controller.model.UserResponse;
import com.kenzie.appserver.repositories.model.CategoryRecord;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/scores/{userId}")
    public ResponseEntity<UserResponse> getTotalPointsbyUserId(@PathVariable("userId") String userId) {
        UserRecord userRecord=
                userService.getUserById(userId); //


        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(UUID.fromString(userRecord.getUserId()));
        userResponse.setUsername(userRecord.getUsername());

        return ResponseEntity.ok(userResponse);
    }







}
