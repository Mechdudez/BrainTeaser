import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import UserClient from "../api/userClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class BrainTeaser extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onGetUser', 'onGetRandomQuestion','onSubmitAnswer',
        'renderExample'], this);
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

        this.dataStore.addChangeListener(this.renderExample)
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderExample() {

        let resultArea = document.getElementById("question-pick")

        const question = this.dataStore.get("question");

        if (question) {
            resultArea.innerHTML = `
                <div>The Question: ${question.questions}</div>
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
                <div>Usernam: ${user.username}</div>
            `
        } else {
            resultArea2.innerHTML = "Question Not Found";
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


   async onGetRandomQuestion(event){
        event.preventDefault();

        let result = await this.client.getRandomQuestion(this.errorHandler);


       await this.dataStore.set("question", result);


       if (result) {
           this.showMessage(`Your new question is ${result}!`)
       } else {
           this.errorHandler("Error fetching your question!  Try" +
               " again...");
       }
    }

    async onSubmitAnswer(event){
        event.preventDefault();
        let category = this.dataStore.get("question");
        this.dataStore.set("answer", null);
        const answers = {}
        let answerResult = "";
        // will need another pojo...
        let questionIdAndAnswer = document.getElementById("answer-field").value;
        let result = await this.client.submitAnswer(questionIdAndAnswer, this.errorHandler);
        if (result == false) {
            answerResult = "You got it SO Wrong, HA HA HA";
        } else {
            answerResult = "What a level of brilliance you are" +
                " displaying so far!";

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

    if (sessionStorage.getItem("userName") == null){
        window.location.href = "login.html";
    }

    await brainTeaser.mount();
};

window.addEventListener('DOMContentLoaded', main);