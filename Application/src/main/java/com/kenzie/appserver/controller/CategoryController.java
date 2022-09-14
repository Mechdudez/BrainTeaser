package com.kenzie.appserver.controller;

import com.kenzie.appserver.service.CategoryService;
import com.kenzie.appserver.controller.model.CategoryResponse;
import com.kenzie.appserver.service.model.Question;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private CategoryService categoryService;

    CategoryController(CategoryService categoryService){

        this.categoryService = categoryService;
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<CategoryResponse> getQuestionById(@PathVariable("questionId") String questionId) {
        Question question =
                categoryService.getQuestionById(questionId); //
        // needs implementation
        if (question == null || question.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setQuestionId(questionId);
        categoryResponse.setQuestions(question.getQuestion());
        categoryResponse.setAnswers(question.getAnswer());
        categoryResponse.setDifficultyOfQuestion(question.getDiffLevel());

        return ResponseEntity.ok(categoryResponse);
    }

}
