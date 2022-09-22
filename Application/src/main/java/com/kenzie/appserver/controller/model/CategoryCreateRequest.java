package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;

public class CategoryCreateRequest {
    @NotEmpty
    @JsonProperty("newQuestion")
    private String newQuestion;

    @JsonProperty("answerKey")
    private String answerKey;

    @JsonProperty("levelOfDifficulty")
    private String levelOfDifficulty;

    public String getNewQuestion() {
        return newQuestion;
    }

    public void setNewQuestion(String newQuestion) {
        this.newQuestion = newQuestion;
    }

    public String getAnswerKey() {
        return answerKey;
    }

    public void setAnswerKey(String answerKey) {
        this.answerKey = answerKey;
    }

    public String getLevelOfDifficulty() {
        return levelOfDifficulty;
    }

    public void setLevelOfDifficulty(String levelOfDifficulty) {
        this.levelOfDifficulty = levelOfDifficulty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryCreateRequest that = (CategoryCreateRequest) o;
        return Objects.equals(newQuestion, that.newQuestion) && Objects.equals(answerKey, that.answerKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(newQuestion, answerKey);
    }
}
