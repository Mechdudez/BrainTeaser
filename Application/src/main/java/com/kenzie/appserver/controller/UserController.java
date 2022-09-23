package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.CategoryResponse;
import com.kenzie.appserver.controller.model.UserCreateRequest;
import com.kenzie.appserver.controller.model.UserResponse;
import com.kenzie.appserver.repositories.model.CategoryRecord;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.UserService;
import com.kenzie.appserver.service.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    UserController(UserService userService) {

        this.userService = userService;
    }



    // TODO SetUserPoints
    // TODO getAllUsers
    // TODO make a class to keep track of user and points.


    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("userId") String userId) {
        User user = userService.getUserById(userId); //

        if(user == null){
            return ResponseEntity.noContent().build();
        }

        UserResponse userResponse = createUserResponse(user);

        return ResponseEntity.ok(userResponse);
    }

    @PostMapping
    public ResponseEntity<UserResponse> addNewUser(@RequestBody UserCreateRequest userCreateRequest) {

        if (userCreateRequest.getUserName() == null || userCreateRequest.getUserName().length() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid UserName");
        }
        User user = new User(userCreateRequest.getUserName(),
                userCreateRequest.getUserId(),
                userCreateRequest.getPoints());
        userService.addNewUser(user);

        UserResponse userResponse = createUserResponse(user);


        return ResponseEntity.created(URI.create("/user/" + userResponse.getUserId())).body(userResponse);
    }


    // Helper methods
    private UserResponse createUserResponse(User user) {

        UserResponse newUserResponse = new UserResponse();
        newUserResponse.setUserId(user.getUserId());
        newUserResponse.setUsername(user.getUserName());
        newUserResponse.setPoints(user.getPoints());

        return newUserResponse;

    }

}
