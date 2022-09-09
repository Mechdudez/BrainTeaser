package com.kenzie.appserver.controller.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryResponse {

    @JsonProperty("questionId")
    private String questionId;

    @JsonProperty
    private String questions;

    @JsonProperty("answers")
    private String answers;

    @JsonProperty("difficultyOfQuestion")
    private String difficultyOfQuestion;

    @JsonProperty("categories")
    private String categories;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public String getDifficultyOfQuestion() {
        return difficultyOfQuestion;
    }

    public void setDifficultyOfQuestion(String difficultyOfQuestion) {
        this.difficultyOfQuestion = difficultyOfQuestion;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }
}
