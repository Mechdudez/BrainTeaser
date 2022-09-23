package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.ExampleRepository;
import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.model.User;
import com.kenzie.capstone.service.client.CheckAnswerServiceClient;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserUnitTests {
    private UserRepository userRepository;
    private UserService userService;
    private CheckAnswerServiceClient checkAnswerServiceClient;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        checkAnswerServiceClient =
                mock(CheckAnswerServiceClient.class);
        userService = new UserService(userRepository,
                checkAnswerServiceClient);
    }

    @Test
    void getUserById(){
        String userId = randomUUID().toString();

        UserRecord userRecord = new UserRecord();
        userRecord.setUserId(UUID.fromString(userId));
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
        String userId = randomUUID().toString();

        // WHEN
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        //UserRecord record = userService.getUserById(userId);

        // THEN
        Assertions.assertThrows(ResponseStatusException.class,
                () -> userService.getUserById(userId),
                "User record Not Found");
    }
}
