package com.kenzie.appserver.controller;

import com.kenzie.appserver.repositories.model.CategoryRecord;
import com.kenzie.appserver.service.CategoryService;
import com.kenzie.appserver.controller.model.CategoryResponse;
import com.kenzie.appserver.service.model.Category;
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
    public ResponseEntity<CategoryResponse> getAnswer(@PathVariable("questionId") String questionId, @PathVariable("answers") String answers){

return null;
}

}
