package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;

public class CategoryCreateRequest {
    @NotEmpty
    @JsonProperty("questionId")
    private String questionId;

    @JsonProperty("questions")
    private String question;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryCreateRequest that = (CategoryCreateRequest) o;
        return Objects.equals(questionId, that.questionId) && Objects.equals(question, that.question);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId, question);
    }
}
