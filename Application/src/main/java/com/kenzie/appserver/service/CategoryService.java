package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.CategoryRepository;
import com.kenzie.appserver.service.model.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public Category getQuestionById(String questionId){


    }
}
