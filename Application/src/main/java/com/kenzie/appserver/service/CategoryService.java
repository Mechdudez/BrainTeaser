package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.CategoryRepository;
import com.kenzie.appserver.service.model.Question;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public Question getQuestionById(String questionId){
        // Example getting data from the local repository
        Question questionFromDynamo = categoryRepository
                .findById(questionId)
                .map(question -> new Question(question.getId(),
                        question.getName()))
                .orElse(null);

        return questionFromDynamo;


    }
}
