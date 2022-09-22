package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.CategoryCreateRequest;
import com.kenzie.appserver.controller.model.UserCreateRequest;
import com.kenzie.appserver.controller.model.UserResponse;
import com.kenzie.appserver.repositories.model.CategoryRecord;
import com.kenzie.appserver.service.CategoryService;
import com.kenzie.appserver.controller.model.CategoryResponse;
import com.kenzie.appserver.service.model.Category;
import com.kenzie.appserver.service.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private CategoryService categoryService;

    CategoryController(CategoryService categoryService) {

        this.categoryService = categoryService;
    }

    // this will get the question by the Id
    @GetMapping("/{questionId}")
    // TODO
    public ResponseEntity<CategoryResponse> getQuestionById(@PathVariable("questionId") String questionId) {
        Category record =
                categoryService.getQuestionById(questionId);
        if (record == null) {
            return ResponseEntity.noContent().build();
        }

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setQuestionId(questionId);
        categoryResponse.setQuestions(record.getQuestions());
        categoryResponse.setAnswers(record.getAnswers());
        categoryResponse.setDifficultyOfQuestion(record.getDifficultyOfAQuestion());

        return ResponseEntity.ok(categoryResponse);
    }

    // This will get the answer to the question.
    @GetMapping("within/{questionId}/{answers}")
    public ResponseEntity<CategoryResponse> getAnswer(@PathVariable("questionId") String question, @PathVariable("answers") String answers) {
        // May need to change questionId to questions.
        Category category = categoryService.getAnswer();

        if (category == null) {
            return ResponseEntity.notFound().build();
        }

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setQuestionId(category.getQuestionId());
        categoryResponse.setQuestions(question);
        // TODO Not sure if it is here or on the front end, but can't show answer right away.
        categoryResponse.setAnswers(answers);
        categoryResponse.setDifficultyOfQuestion(category.getDifficultyOfAQuestion());

        return ResponseEntity.ok(categoryResponse);
    }

//    @PostMapping("{userId}/{questionId}/{answers}")
//    public ResponseEntity<CategoryResponse> getUserAnswer(@PathVariable("questionId") String questionId, @PathVariable("answers") String answers) {
//
//        return null;
//    }

    // this will randomly group a question
    @GetMapping("/random")
    public ResponseEntity<CategoryResponse> getRandomQuestion() {
        Category category = categoryService.getRandomQuestion();

        if (category == null) {
            return ResponseEntity.noContent().build();
        }
        CategoryResponse categoryResponse = createCategoryResponse(category);

        return ResponseEntity.ok(categoryResponse);
    }


   // This will grab all the questions in the DB.
    @GetMapping("/all")
    public ResponseEntity<List<CategoryResponse>> getAllQuestions() {
        List<Category> categories = categoryService.getAllQuestions();

        if (categories == null || categories.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<CategoryResponse> response = new ArrayList<>();

        for (Category category : categories) {
            // Need helper method
            response.add(this.createCategoryResponse(category));
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<CategoryResponse> createOneQuestion(@RequestBody CategoryCreateRequest catetoryCreateRequest){

        if (catetoryCreateRequest.getNewQuestion() == null || catetoryCreateRequest.getNewQuestion().length() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid question creation request!");
        }

        Category question =
                new Category(randomUUID().toString(),
                        catetoryCreateRequest.getNewQuestion(),
                                                  catetoryCreateRequest.getAnswerKey(),
                        catetoryCreateRequest.getLevelOfDifficulty());

        categoryService.createOneQuestion(question);

        CategoryResponse response = createCategoryResponse(question);


        return ResponseEntity.created(URI.create("/create" + response.getQuestionId())).body(response);

    }



    // Helper methods
    private CategoryResponse createCategoryResponse(Category category) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setQuestionId(category.getQuestionId());
        categoryResponse.setQuestions(category.getQuestions());
        categoryResponse.setAnswers(category.getAnswers());
        categoryResponse.setDifficultyOfQuestion(category.getDifficultyOfAQuestion());

        return categoryResponse;

    }


}
