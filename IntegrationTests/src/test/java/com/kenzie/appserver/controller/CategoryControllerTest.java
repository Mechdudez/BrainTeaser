package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.CategoryCreateRequest;
import com.kenzie.appserver.repositories.model.CategoryRecord;
import com.kenzie.appserver.service.CategoryService;
import com.kenzie.appserver.service.model.Category;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class CategoryControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    CategoryService categoryService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getQuestionById_Exists() throws Exception {
        Integer questionId = 1;
        String question = "How many days in a week?";
        String difficultyOfAQuestion = "easy";
        String answer = "7";

        Category category = new Category(questionId, question, difficultyOfAQuestion, answer);

        CategoryRecord persistedCategory = categoryService.createOneQuestion(category);
        // WHEN
        mvc.perform(get("/category/{questionId}", persistedCategory.getQuestionId())
                        .accept(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(jsonPath("questionId")
                        .value(is(questionId)))
                .andExpect(jsonPath("questions")
                        .value(is(question)))
                .andExpect(jsonPath("difficultyOfAQuestion")
                        .value(is(difficultyOfAQuestion)))
                .andExpect(jsonPath("answers")
                        .value(is(answer)))
                .andExpect(status().isOk());
    }

    @Test
    public void getQuestion_QuestionDoesNotExist() throws Exception {
        // GIVEN
        Integer questionId = 1;
        // WHEN
        mvc.perform(get("/category/{questionId}",questionId)
                        .accept(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isNotFound());
    }

    @Test
    public void createQuestion_CreateSuccessful() throws Exception {
        // GIVEN
        Integer questionId = 1;
        String question = "How many days in a week?";
        String difficultyOfAQuestion = "easy";
        String answer = "7";

        CategoryCreateRequest categoryCreateRequest = new CategoryCreateRequest();
        categoryCreateRequest.setQuestionId(questionId);
        categoryCreateRequest.setQuestions(question);
        categoryCreateRequest.setLevelOfADifficulty(difficultyOfAQuestion);
        categoryCreateRequest.setAnswers(answer);

        mapper.registerModule(new JavaTimeModule());

        // WHEN
        mvc.perform(post("/category")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(categoryCreateRequest)))
                // THEN
                .andExpect(jsonPath("questionId")
                        .value(is(questionId)))
                .andExpect(jsonPath("questions")
                        .value(is(question)))
                .andExpect(jsonPath("difficultyOfAQuestion")
                        .value(is(difficultyOfAQuestion)))
                .andExpect(jsonPath("answers")
                        .value(is(answer)))
                .andExpect(status().isCreated());
    }

    @Test
    public void findAllQuestions_Successful() throws Exception {
        // GIVEN
        Integer questionId = 1;
        String question = "How many days in a week?";
        String difficultyOfAQuestion = "easy";
        String answer = "7";

        Integer questionId2 = 2;
        String question2 = "How many weeks in a year?";
        String difficultyOfAQuestion2 = "easy";
        String answer2 = "52";

        Category category = new Category(questionId,question, difficultyOfAQuestion, answer);
        Category category2 = new Category(questionId2, question2, difficultyOfAQuestion2, answer2);
        categoryService.createOneQuestion(category);
        categoryService.createOneQuestion(category2);

        List<Category> categoryList = categoryService.getAllQuestions();

        // WHEN
        mvc.perform(get("/category/all", categoryList)
                        .accept(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isOk());
    }
}
