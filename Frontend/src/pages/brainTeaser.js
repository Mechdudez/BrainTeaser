import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import UserClient from "../api/userClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class BrainTeaser extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onGetUser', 'onGetRandomQuestion', 'onSubmitAnswer',
            'renderQuestion'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('get-user-points-by-id-form').addEventListener('submit', this.onGetUser);
        document.getElementById('random-question-button').addEventListener('click', this.onGetRandomQuestion);
        document.getElementById('get-your-answer-form').addEventListener('submit', this.onSubmitAnswer);
        this.client = new UserClient();

        this.dataStore.addChangeListener(this.renderQuestion)
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderQuestion() {

        let resultArea = document.getElementById("question-pick")

        const question = this.dataStore.get("question");

        if (question) {
            resultArea.innerHTML = `
                <div>Question: ${question.questions}</div>
                <div>Difficulty Level: ${question.difficultyOfAQuestion}</div>
                <div>Question ID: ${question.questionId}</div>
            `
        } else {
            resultArea.innerHTML = "Question Not Found";
        }

        let resultArea2 = document.getElementById("result-info");
        const user = this.dataStore.get("user");

        if (user) {
            resultArea2.innerHTML = `
                <div>User ID: ${user.userId}</div>
                <div>Username: ${user.username}</div>
            `
        } else {
            resultArea2.innerHTML = "User Not Found";
        }

        let answerArea = document.getElementById("answer-result");
        const answer = this.dataStore.get("answer");
        if (answer) {
            answerArea.innerHTML = `
                <div>Answer Result: ${answer.toString()}</div>
                
            `
        } else {
            answerArea.innerHTML = "thinking out loud ... ";
        }


    }

    // Event Handlers --------------------------------------------------------------------------------------------------


    async onGetRandomQuestion(event) {
        event.preventDefault();

        let result = await this.client.getRandomQuestion(this.errorHandler);
        this.dataStore.set("question", result);

        if (result) {
            this.showMessage(`Your new question is ${result}!`)
        } else {
            this.errorHandler("Error fetching your question!  Try" +
                " again...");
        }
    }

    async onSubmitAnswer(event) {
        event.preventDefault();
        let category = this.dataStore.get("question");
        this.dataStore.set("answer", null);
        const correctAnswers = ['You have hit a nail on the head.', 'Yes, that’s very correct.', 'You are quite right.',
            'Great job! You got it', 'That’s spot on.'];
        const wrongAnswers = ['You thought wrong!', 'Sorry that is the incorrect answer', 'oooo sorry, so close',
            'Would you mind to think twice on what you’re saying', 'Where did you hear that?'];
        let answerResult = "";
        // will need another pojo...
        let questionIdAndAnswer = document.getElementById("answer-field").value;
        let result = await this.client.submitAnswer(questionIdAndAnswer, this.errorHandler);
        if (result == false) {
            answerResult = wrongAnswers[Math.floor(Math.random() * wrongAnswers.length)]
        } else {
            answerResult = correctAnswers[Math.floor(Math.random() * correctAnswers.length)]
        }

        this.dataStore.set("answer", answerResult);


        if (answerResult) {
            this.showMessage(`Your submission result is ${result}!`)
        } else {
            this.errorHandler("Error fetching your result!  Try" +
                " again...");
        }


    }


    async onGetUser(event) {

        event.preventDefault();
        this.dataStore.set("user", null);
        let userId = document.getElementById("userid-field").value;


        let result = await this.client.getUserById(userId, this.errorHandler);
        this.dataStore.set("user", result);
        if (result) {
            this.showMessage(`Got ${result}!`)
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const brainTeaser = new BrainTeaser();

    if (sessionStorage.getItem("userName") == null) {
        window.location.href = "login.html";
    }

    await brainTeaser.mount();
};

window.addEventListener('DOMContentLoaded', main);