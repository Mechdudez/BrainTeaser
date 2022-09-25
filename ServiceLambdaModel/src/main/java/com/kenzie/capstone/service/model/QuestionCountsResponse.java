package com.kenzie.capstone.service.model;

public class QuestionCountsResponse {

        private Integer questionId;
        private Integer countsPicked;

        public QuestionCountsResponse(Integer questionId,
                                      Integer countsPicked
        ) {
            this.questionId = questionId;
            this.countsPicked = countsPicked;
        }


        public QuestionCountsResponse(){}

        public Integer getQuestionId() {
            return questionId;
        }

        public void setQuestionId(Integer questionId) {
            this.questionId = questionId;
        }

        public Integer getQuestionCounts() {
            return countsPicked;
        }

        public void setQuestionCounts(Integer countsPicked) {
            this.countsPicked = countsPicked;
        }
        @Override
        public String toString() {
            return "UserAnswerResponse{" +
                    ", questionId='" + questionId + '\'' +
                    ", questionCounts='" + countsPicked + '\'' +
                    '}';
        }
}
