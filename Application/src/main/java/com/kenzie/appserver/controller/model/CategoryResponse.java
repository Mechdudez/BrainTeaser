package com.kenzie.appserver.controller.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryResponse {

    @JsonProperty("questionId")
    private Integer questionId;

    @JsonProperty ("questions")
    private String questions;

    @JsonProperty("answers")
    private String answers;

    @JsonProperty("difficultyOfAQuestion")
    private String difficultyOfAQuestion;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
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
        return difficultyOfAQuestion;
    }

    public void setDifficultyOfQuestion(String difficultyOfQuestion) {
        this.difficultyOfAQuestion = difficultyOfQuestion;
    }

}
