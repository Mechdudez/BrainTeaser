package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.ExampleRepository;
import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.CategoryRecord;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.model.Category;
import com.kenzie.appserver.service.model.User;
import com.kenzie.capstone.service.client.CheckQuestionCountsServiceClient;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.*;

public class UserUnitTests {
    private UserRepository userRepository;
    private UserService userService;
    private CheckQuestionCountsServiceClient checkQuestionCountsServiceClient;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        checkQuestionCountsServiceClient =
                mock(CheckQuestionCountsServiceClient.class);
        userService = new UserService(userRepository);
    }

    /** ------------------------------------------------------------------------
     *  userService.getUserById
     *  ------------------------------------------------------------------------ **/

    @Test
    void getUserById(){
        UUID userId = randomUUID();

        UserRecord userRecord = new UserRecord();
        userRecord.setUserId(userId);
        userRecord.setUsername("randoJohn");
        userRecord.setPoints(5);

        // WHEN
        when(userRepository.findById(userId)).thenReturn(Optional.of(userRecord));
        User user = userService.getUserById(userId);

        //Then
        Assertions.assertNotNull(user, "The object record is " +
                "returned");
        Assertions.assertEquals(userRecord.getUserId(),
                user.getUserId(),
                "The id matches");
        Assertions.assertEquals(userRecord.getUsername(),
                user.getUserName(), "The name matches");

    }

    @Test
    void getUserById_invalid_customer() {
        // GIVEN
        UUID userId = randomUUID();

        // WHEN
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        //UserRecord record = userService.getUserById(userId);

        // THEN
        Assertions.assertThrows(UserNotFoundException.class,
                () -> userService.getUserById(userId),
                "User record Not Found");
    }

    /** ------------------------------------------------------------------------
     *  userService.addNewUser
     *  ------------------------------------------------------------------------ **/

    @Test
    void addNewUser() {
        // GIVEN
        User user = new User("Tom", UUID.randomUUID(), 0);

        // WHEN
        User newUser = userService.addNewUser(user);
        // THEN
        Assertions.assertNotNull(newUser, "The user returned");
        Assertions.assertEquals(newUser.getUserName(), user.getUserName(), "The user name matches");
        Assertions.assertEquals(newUser.getUserId(), user.getUserId(), "The user id matches");
        Assertions.assertEquals(newUser.getPoints(), user.getPoints(), "The points matches");

    }

    /** ------------------------------------------------------------------------
     *  userService.getAllUsers
     *  ------------------------------------------------------------------------ **/

    @Test
    void getAllUsers_returnsMultipleUsers() {
        // GIVEN
        UserRecord user1 = new UserRecord();
        user1.setUsername("Tom");
        user1.setUserId(UUID.randomUUID());
        user1.setPoints(0);

        UserRecord user2 = new UserRecord();
        user2.setUsername("Jerry");
        user2.setUserId(UUID.randomUUID());
        user2.setPoints(0);


        List<UserRecord> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        // WHEN
        when(userRepository.findAll()).thenReturn(userList);
        userService.getAllUsers();

        // THEN
        Assertions.assertNotNull(userList, "All users are returned");
        Assertions.assertEquals(2, userList.size(), "There are two questions in the list");

        for (UserRecord user : userList) {
            if (user1.getUsername() == user.getUsername()) {
                Assertions.assertEquals(user1.getUserId(), user.getUserId(), "The user id matches");
                Assertions.assertEquals(user1.getPoints(), user.getPoints(), "The user points matches");


            } else if (user2.getUsername() == user.getUsername()) {
                Assertions.assertEquals(user2.getUserId(), user.getUserId(), "The user id matches");
                Assertions.assertEquals(user2.getPoints(), user.getPoints(), "The user points matches");
            } else {
                Assertions.assertTrue(false, "Question returned that was not in the records!");
            }
        }
    }

    /** ------------------------------------------------------------------------
     *  userService.getTopScores
     *  ------------------------------------------------------------------------ **/
//    @Test
//    void getTopScores() {
//        // GIVEN
//        UserRecord user1 = new UserRecord();
//        user1.setUsername("Tom");
//        user1.setUserId(UUID.randomUUID());
//        user1.setPoints(3);
//
//        UserRecord user2 = new UserRecord();
//        user2.setUsername("Jerry");
//        user2.setUserId(UUID.randomUUID());
//        user2.setPoints(1);
//
//        UserRecord user3 = new UserRecord();
//        user2.setUsername("Sam");
//        user2.setUserId(UUID.randomUUID());
//        user2.setPoints(2);
//
//        UserRecord user4 = new UserRecord();
//        user2.setUsername("Max");
//        user2.setUserId(UUID.randomUUID());
//        user2.setPoints(5);
//
//        UserRecord user5 = new UserRecord();
//        user2.setUsername("Mike");
//        user2.setUserId(UUID.randomUUID());
//        user2.setPoints(4);
//
//        UserRecord user6 = new UserRecord();
//        user2.setUsername("Tim");
//        user2.setUserId(UUID.randomUUID());
//        user2.setPoints(0);
//
//
//        List<UserRecord> userList = new ArrayList<>();
//        userList.add(user1);
//        userList.add(user2);
//        userList.add(user3);
//        userList.add(user4);
//        userList.add(user5);
//        userList.add(user6);
//
//        // WHEN
//        when(userRepository.findAll()).thenReturn(userList);
//        List<User> topScores = userService.getTopScores();
//
//
//        // THEN
//        Assertions.assertNotNull(userList, "User list in not null");
//        Assertions.assertEquals(5, userList.size(), "There are 5 users in the list");
//    }

}
