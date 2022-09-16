package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.CategoryResponse;
import com.kenzie.appserver.controller.model.UserCreateRequest;
import com.kenzie.appserver.controller.model.UserResponse;
import com.kenzie.appserver.repositories.model.CategoryRecord;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
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

    @PostMapping
    public ResponseEntity<UserResponse> addNewUser(@RequestBody UserCreateRequest userCreateRequest) {

        if (userCreateRequest.getUserName() == null || userCreateRequest.getUserName().length() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid UserName");
        }


        UserRecord newUserRecord =
                userService.addNewUser(userCreateRequest);

        UserResponse newUserResponse = new UserResponse();
        newUserResponse.setUserId(UUID.fromString(newUserRecord.getUserId()));
        newUserResponse.setUsername(newUserRecord.getUsername());
        newUserResponse.setPoints(newUserResponse.getPoints());

        return ResponseEntity.created(URI.create("/users/" + newUserResponse.getUserId())).body(newUserResponse);
    }



}
