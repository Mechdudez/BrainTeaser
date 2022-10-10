package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.QueryUtility;
import com.kenzie.appserver.controller.model.CategoryCreateRequest;
import com.kenzie.appserver.controller.model.UserCreateRequest;
import com.kenzie.appserver.repositories.model.CategoryRecord;
import com.kenzie.appserver.service.CategoryService;
import com.kenzie.appserver.service.UserService;
import com.kenzie.appserver.service.model.Category;
import com.kenzie.appserver.service.model.User;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    UserService userService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private QueryUtility queryUtility;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup(){
        queryUtility = new QueryUtility(mvc);

    }

    @Test
    public void getUserById_Exists() throws Exception {
        UserCreateRequest userCreateRequest = new UserCreateRequest();
        userCreateRequest.setUserId(UUID.randomUUID());
        userCreateRequest.setUserName(mockNeat.strings().get());
        userCreateRequest.setPoints(mockNeat.ints().get());

        queryUtility.userControllerClient.createUser(userCreateRequest);
        // WHEN
        queryUtility.userControllerClient.getUser(userCreateRequest.getUserId())
                // THEN
//                .andExpect(jsonPath("userId")
//                        .value(is(userCreateRequest.getUserId())))
//                .andExpect(jsonPath("points")
//                        .value(is(userCreateRequest.getPoints())))
//                .andExpect(jsonPath("userName")
//                        .value(is(userCreateRequest.getUserName())))
                .andExpect(status().isOk());
    }

    @Test
    public void getUser_UserDoesNotExist() throws Exception {
        // GIVEN
        UUID userId = UUID.randomUUID();
        // WHEN
        queryUtility.userControllerClient.getUser(userId)
                // THEN
                .andExpect(status().isNotFound());
    }

    @Test
    public void createUser_CreateSuccessful() throws Exception {
        // GIVEN
        UserCreateRequest userCreateRequest = new UserCreateRequest();
        userCreateRequest.setUserId(UUID.randomUUID());
        userCreateRequest.setUserName(mockNeat.strings().get());
        userCreateRequest.setPoints(mockNeat.ints().get());

        // WHEN
        queryUtility.userControllerClient.createUser(userCreateRequest)
                // THEN
//                .andExpect(jsonPath("userId")
//                        .value(is(userCreateRequest.getUserId())))
//                .andExpect(jsonPath("points")
//                        .value(is(userCreateRequest.getPoints())))
//                .andExpect(jsonPath("userName")
//                        .value(is(userCreateRequest.getUserName())))
                .andExpect(status().isCreated());
    }

    @Test
    public void findAllUsers_Successful() throws Exception {
        // GIVEN
        String userName = "Tom";
        UUID userId = UUID.randomUUID();
        Integer points = 0;

        String userName2 = "Jerry";
        UUID userId2 = UUID.randomUUID();
        Integer points2 = 0;

        User user = new User(userName, userId, points);
        User user2 = new User(userName2, userId2, points2);
        userService.addNewUser(user);
        userService.addNewUser(user2);

        List<User> userList = userService.getAllUsers();

        // WHEN
        mvc.perform(get("/user/all", userList)
                        .accept(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isOk());
    }
}
