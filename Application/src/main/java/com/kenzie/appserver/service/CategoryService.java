package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.CategoryRepository;
import com.kenzie.appserver.repositories.model.CategoryRecord;
import com.kenzie.appserver.service.model.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public CategoryRecord getQuestionById(String questionId){
        // getting data from the local repository
        if(categoryRepository
                .findById(questionId).isPresent()){
            CategoryRecord questionRecord = categoryRepository
                    .findById(questionId).get();
            return questionRecord;
        }

        return null;


    }
}
