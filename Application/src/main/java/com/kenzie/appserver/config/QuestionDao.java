package com.kenzie.appserver.config;

import com.kenzie.appserver.repositories.model.CategoryRecord;

public interface QuestionDao {

    CategoryRecord addQuestion(CategoryRecord category);
}
