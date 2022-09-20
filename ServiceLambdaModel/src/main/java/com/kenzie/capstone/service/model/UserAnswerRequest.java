package com.kenzie.capstone.service.model;

public class UserAnswerRequest {
    private String userId;
    private String questionId;

    private String userAnswer;

    private Boolean result;


    public UserAnswerRequest(String userId, String questionId,
                             String userAnswer, Boolean result){
        this.userId = userId;
        this.questionId = questionId;
        this.userAnswer = userAnswer;
        this.result = result;
    }

    public UserAnswerRequest(){}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }


    public Boolean getResult() {
        // change to this later
//        int matchingScore = FuzzySearch.ratio("mysmilarstring",
//                "mysimilarstring");
//        if(matchingScore >= 90){
//            return Boolean.TRUE;
//        }
//        return Boolean.FALSE;
        return result;
    }

    public void setResult(){
        this.result = result;
    }

    @Override
    public String toString() {
        return "UserAnswerRequest{" +
                "userId='" + userId + '\'' +
                ", questionId='" + questionId + '\'' +
                ", userAnswer='" + userAnswer + '\'' +
                ", result='" + result + '\'' +
                '}';
    }

}
