package com.kenzie.appserver.controller;

import com.kenzie.appserver.repositories.model.CategoryRecord;
import com.kenzie.appserver.service.CategoryService;
import com.kenzie.appserver.controller.model.CategoryResponse;
import com.kenzie.appserver.service.model.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/Category")
public class CategoryController {

    private CategoryService categoryService;

    CategoryController(CategoryService categoryService) {

        this.categoryService = categoryService;
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<CategoryResponse> getQuestionById(@PathVariable("questionId") String questionId) {
        CategoryRecord record =
                categoryService.getQuestionById(questionId); //
        // needs implementation
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

    @GetMapping("within/{questionId}/{answers}")
    public ResponseEntity<CategoryResponse> getAnswer(@PathVariable("questionId") String questionId, @PathVariable("answers") String answers) {

        return null;
    }

    @GetMapping("all")
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
