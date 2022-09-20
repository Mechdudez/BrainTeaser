package com.kenzie.appserver.service;


import com.kenzie.appserver.repositories.CategoryRepository;
import com.kenzie.capstone.service.client.CheckAnswerServiceClient;
import com.kenzie.appserver.repositories.model.CategoryRecord;
import com.kenzie.appserver.service.model.Category;
import com.kenzie.capstone.service.model.UserAnswerRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    private CheckAnswerServiceClient checkAnswerServiceClient;


    public CategoryService(CategoryRepository categoryRepository, CheckAnswerServiceClient checkAnswerServiceClient) {
        this.categoryRepository = categoryRepository;
        this.checkAnswerServiceClient = checkAnswerServiceClient;

    }

    public CategoryRecord getQuestionById(String questionId) {
        // getting data from the local repository
        if (categoryRepository
                .findById(questionId).isPresent()) {
            CategoryRecord questionRecord = categoryRepository
                    .findById(questionId).get();
            return questionRecord;
        }

        return null;

    }


    // Will need to find a way to random choose one of the questions in the list.
    public Category getRandomQuestion(){
        List<Category> categoryList = getAllQuestions();
        Random random = new Random();
        if(categoryList.isEmpty()){
            throw new CategoryNotFoundException("Sorry there seems to be no questions");
        }else{
            // need to return a single question
            return categoryList.get(random.nextInt(categoryList.size()));
        }

    }

    // will be user to generate all the questions
    public List<Category> getAllQuestions(){
        List<Category> categoryList = new ArrayList<>();

        List<CategoryRecord> recordList = new ArrayList<>();
        categoryRepository.findAll()
                .forEach(recordList::add);

        for(CategoryRecord categoryRecord : recordList){
            categoryList.add(new Category(categoryRecord.getQuestionId(), categoryRecord.getQuestions(), categoryRecord.getAnswers(), categoryRecord.getDifficultyOfAQuestion()));
        }

        return categoryList;
    }
    public Category getAnswer(String questionId, String answer){

        return null;
    }

    public Boolean checkAnswer(String userId, String questionId,
                               String userAnswer, String answerKey){

            // need to implement fuzzy match later
            Boolean result = userAnswer == answerKey;
            UserAnswerRequest userAnswerRequest =
                    new UserAnswerRequest(userId, questionId,
                            userAnswer, result);
            checkAnswerServiceClient.addUserAnswer(userAnswerRequest);

            return result;

    }



    // user answers question and gets feedback on answer.
    // if the user answers correctly move difficulty up and give user 1 point.
    // if user answers incorrectly show user correct answer then move the difficulty down a level.
    public void getUserAnswer(Category question) {

        // I will need to create 5 different ways the user will be given if they are wrong or right
        // I will need a scanner object in order to see the user answer
        // I will need a way for the question to match up with the answer. Maybe method isn't created yet?
        Scanner myScanner = new Scanner(System.in);
        String userName = myScanner.next();
        String answer = question.getAnswers();

        if (userName.equals(answer)) {
            helperMethodForCorrectAnswer();
            // if the user gets the correct answer we want to make it more difficult for the user next time
            if(question.getDifficultyOfAQuestion().equals("easy")){
                question.setDifficultyOfAQuestion("hard");
            } else if(question.getDifficultyOfAQuestion().equals("hard")){
                // if use is already on hard do nothing
            }
        } else {
            helperMethodForIncorrectAnswer();
            // shows user correct answer
            System.out.println("This is the correct answer " + answer);
            // if they are already on an easier question, give them another easy question.
            if (question.getDifficultyOfAQuestion().equals("easy")) {
                // do nothing they will need an easy question again
            } else if (question.getDifficultyOfAQuestion().equals("hard")) {
                // if player is on a hard question, make the next question an easier question.
                question.setDifficultyOfAQuestion("easy"); // seems wrong, will revisit
                // may have to set whole thing
            }
        }
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
