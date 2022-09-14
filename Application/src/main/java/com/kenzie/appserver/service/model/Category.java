package com.kenzie.appserver.service.model;

import java.util.Objects;

public class Category {

    private String questionId;

    private String questions;

    private String difficultyOfAQuestion;

    private  String answers;

    public Category(String questionId, String questions, String difficultyOfAQuestion, String answers) {
        this.questionId = questionId;
        this.questions = questions;
        this.difficultyOfAQuestion = difficultyOfAQuestion;
        this.answers = answers;
    }

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

    public String getDifficultyOfAQuestion() {
        return difficultyOfAQuestion;
    }

    public void setDifficultyOfAQuestion(String difficultyOfAQuestion) {
        this.difficultyOfAQuestion = difficultyOfAQuestion;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(questionId, category.questionId) && Objects.equals(questions, category.questions) && Objects.equals(difficultyOfAQuestion, category.difficultyOfAQuestion) && Objects.equals(answers, category.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId, questions);
    }
}
