package com.kenzie.appserver.service;


import com.kenzie.appserver.repositories.CategoryRepository;
import com.kenzie.capstone.service.client.CheckQuestionCountsServiceClient;
import com.kenzie.appserver.repositories.model.CategoryRecord;
import com.kenzie.appserver.service.model.Category;
import com.kenzie.capstone.service.model.QuestionCountsRequest;
import com.kenzie.capstone.service.model.QuestionCountsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.UUID.randomUUID;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    private CheckQuestionCountsServiceClient questionCountsServiceClient;


    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CheckQuestionCountsServiceClient questionCountsServiceClient) {
        this.categoryRepository = categoryRepository;
        this.questionCountsServiceClient = questionCountsServiceClient;

    }

    //TODO write a service for the Lambda to call
    public Category getQuestionById(Integer questionId) {
        // getting data from the local repository

        Category getCategory = categoryRepository.findById(questionId)
                .map(category -> new Category(category.getQuestionId(), category.getQuestions(), category.getDifficultyOfAQuestion(), category.getAnswers()))
                .orElse(null);


        if (getCategory == null) {
            throw new CategoryNotFoundException("There is no such question");
        }

        // lambda function that tabs frequency of each question
        // being picked by the user
        QuestionCountsRequest chooseQuestionRequest =
                new QuestionCountsRequest();
        chooseQuestionRequest.setQuestionId(questionId);
        questionCountsServiceClient.countQuestionsChosen(chooseQuestionRequest);

        return getCategory;

    }


    public CategoryRecord createOneQuestion(Category newQuestion){

//        System.out.println("debugging new question in CreateOneQ " + newQuestion.getQuestionId());
//        System.out.println("debugging new question in CreateOneQ " + newQuestion.getAnswers());
        CategoryRecord newRecord = new CategoryRecord();
        newRecord.setQuestionId(newQuestion.getQuestionId());
        newRecord.setQuestions(newQuestion.getQuestions());
        newRecord.setAnswers(newQuestion.getAnswers());
        newRecord.setDifficultyOfAQuestion(newQuestion.getDifficultyOfAQuestion());

//        System.out.println("debugging record id in CreateOneQ " + newRecord.getQuestionId());
//        System.out.println("debugging record answer  in CreateOneQ " + newRecord.getAnswers());
        categoryRepository.save(newRecord);

        return newRecord;

    }


    // Will need to find a way to random choose one of the questions in the list.
    public Category getRandomQuestion() {
        // Grab all the questions
        List<Category> categoryList = getAllQuestions();
        Random random = new Random();
        if (categoryList.isEmpty()) {
            throw new CategoryNotFoundException("Sorry there seems to be no questions");
        } else {
            // need to return a single question
            // will randomly grab a question based on the size of the list.
            return categoryList.get(random.nextInt(categoryList.size()));
        }

    }

    // will be user to generate all the questions
    public List<Category> getAllQuestions() {
        List<Category> categoryList = new ArrayList<>();

        // iterate through each question and add them to a list.
        List<CategoryRecord> recordList = new ArrayList<>();
        categoryRepository.findAll()
                .forEach(recordList::add);
        // add the list into a new category record
        for (CategoryRecord categoryRecord : recordList) {
            categoryList.add(new Category(categoryRecord.getQuestionId(), categoryRecord.getQuestions(), categoryRecord.getAnswers(), categoryRecord.getDifficultyOfAQuestion()));
        }

        return categoryList; // return that list.
    }


    // TODO if time allows do this.
//    public Boolean checkAnswer(String userId, String questionId,
//                               String userAnswer, String answerKey) {
//
//        // need to implement fuzzy match later
//        Boolean result = userAnswer == answerKey;
//        UserAnswerRequest userAnswerRequest =
//                new UserAnswerRequest(userId, questionId,
//                        userAnswer, result);
//        checkAnswerServiceClient.addUserAnswer(userAnswerRequest);
//
//        return result;
//
//    }


    // TODO needs to be done still
    public Category getAnswer(String answers) {
        Category categories = getRandomQuestion();
        Scanner myScanner = new Scanner(System.in);
        String userName = myScanner.nextLine();
        int points = 0;

        // make sure the user answer and answer are equal.
        // if user is correct, add 1 point
        if (userName.equals(categories.getAnswers())) {

            helperMethodForCorrectAnswer();
            points++;
        }
        // if they are incorrect, don't add anything
        else {
            helperMethodForIncorrectAnswer();
        }

        return null;
    }

    // helper method to get correct answer.
    private String helperMethodForCorrectAnswer() {

        String[] arrayRightAnswers = new String[]{"You have hit a nail on the head.", "Yes, that’s very correct.", "You are quite right.",
                "Great job! You got it", "That’s spot on."};

        return arrayRightAnswers[(int) (Math.random() * arrayRightAnswers.length)];
    }

    // helper method to get incorrect answer
    private String helperMethodForIncorrectAnswer() {
        String[] arrayWrongAnswers = new String[]{"You thought wrong!", "Sorry that is the incorrect answer", "oooo sorry, so close",
                "Would you mind to think twice on what you’re saying", "Where did you hear that?"};

        return arrayWrongAnswers[(int) (Math.random() * arrayWrongAnswers.length)];
    }

}
