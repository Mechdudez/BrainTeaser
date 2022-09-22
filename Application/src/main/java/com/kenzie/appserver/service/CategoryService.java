package com.kenzie.appserver.service;


import com.kenzie.appserver.repositories.CategoryRepository;
import com.kenzie.capstone.service.client.CheckAnswerServiceClient;
import com.kenzie.appserver.repositories.model.CategoryRecord;
import com.kenzie.appserver.service.model.Category;
import com.kenzie.capstone.service.model.UserAnswerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    private CheckAnswerServiceClient checkAnswerServiceClient;


    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CheckAnswerServiceClient checkAnswerServiceClient) {
        this.categoryRepository = categoryRepository;
        this.checkAnswerServiceClient = checkAnswerServiceClient;

    }

    //TODO write a service for the Lambda to call

    // TODO
    public Category getQuestionById(String questionId) {
        // getting data from the local repository
        Category categoryId = categoryRepository.findById(questionId)
                .map(category -> new Category(category.getQuestionId(), category.getQuestions(), category.getAnswers(), category.getDifficultyOfAQuestion()))
                .orElse(null);



            if(categoryId == null){
                throw new CategoryNotFoundException("There is no such question");
            }


        return categoryId;

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

    public Category getAnswer() {
       Category categories = getRandomQuestion();
        Scanner myScanner = new Scanner(System.in);
        String userName = myScanner.nextLine();
        int points = 0;

        // make sure the user answer and answer are equal.
        // if user is correct, add 1 point
        if(userName.equals(categories.getAnswers())){

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
