package com.kenzie.appserver.controller;

import com.kenzie.appserver.service.model.CategoryService;
import com.kenzie.appserver.controller.model.CategoryResponse;
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
    public ResponseEntity<CategoryResponse> getQuestionById(@PathVariable("questionId") int questionId) {
        CategoryResponse question =
                categoryService.findQuestionsByID(questionId); //
        // needs implementation
        if (question == null || question.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(question);
    }

}
