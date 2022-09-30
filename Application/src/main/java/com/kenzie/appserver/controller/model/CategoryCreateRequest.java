package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;

public class CategoryCreateRequest {
    @NotEmpty
    @JsonProperty("questionId")
    private Integer questionId;
    @JsonProperty("questions")
    private String questions;

    @JsonProperty("answers")
    private String answers;

    @JsonProperty("levelOfADifficulty")
    private String levelOfADifficulty;


    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String newQuestion) {
        this.questions = newQuestion;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public String getLevelOfADifficulty() {
        return levelOfADifficulty;
    }

    public void setLevelOfADifficulty(String levelOfDifficulty) {
        this.levelOfADifficulty = levelOfDifficulty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryCreateRequest that = (CategoryCreateRequest) o;
        return Objects.equals(questions, that.questions) && Objects.equals(answers, that.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questions, answers);
    }
}
