package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

@DynamoDBTable(tableName = "Category")
public class CategoryRecord {

    String questionId;

    String questions;

    String DifficultyOfAQuestion;

    String answers;

    String categories;

    @DynamoDBHashKey(attributeName = "questionId")
    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
    @DynamoDBAttribute(attributeName = "questions")
    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    @DynamoDBAttribute(attributeName = "difficultyOfAQuestion")
    public String getDifficultyOfAQuestion() {
        return DifficultyOfAQuestion;
    }

    public void setDifficultyOfAQuestion(String difficultyOfAQuestion) {
        DifficultyOfAQuestion = difficultyOfAQuestion;
    }

    @DynamoDBAttribute(attributeName = "answers")
    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }
    @DynamoDBAttribute(attributeName = "categories")
    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryRecord category = (CategoryRecord) o;
        return Objects.equals(questionId, category.questionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId);
    }
}
