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
        // this.onGetUser = this.onGetUser.bind(this);
        // this.renderExample = this.renderExample.bind(this);
        // this.
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('get-user-points-by-id-form').addEventListener('submit', this.onGetUser);
        document.getElementById('get-random-question-form').addEventListener('submit', this.onGetRandomQuestion);
        document.getElementById('get-your-answer-form').addEventListener('submit', this.onSubmitAnswer);
        this.client = new UserClient();

        this.dataStore.addChangeListener(this.renderExample)
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderExample() {
        //let resultArea = document.getElementById("result-info");
        //const user = this.dataStore.get("user");
        let resultArea = document.getElementById("question-pick")

        const question = this.dataStore.get("question");

        if (question) {
            resultArea.innerHTML = `
                <div>${question.questions}</div>
                <div>${question.difficultyOfAQuestion}</div>
                <div>Question ID: ${question.questionId}</div>
                <div>Answer: ${question.answers}</div>
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


    }

    // Event Handlers --------------------------------------------------------------------------------------------------


   async onGetRandomQuestion(event){
        event.preventDefault();


       let questionId = document.getElementById("question-field").value;
        let result = await this.client.getRandomQuestion(this.errorHandler);


       await this.dataStore.set("question", result.questions);


       if (result) {
           this.showMessage(`Your new question is ${result}!`)
       } else {
           this.errorHandler("Error fetching your question!  Try" +
               " again...");
       }
    }

    async onSubmitAnswer(event){
        event.preventDefault();
        this.dataStore.set("answer", null);

        let questionIdAndAnswer = document.getElementById("answer-field").value;
        let result = await this.client.submitAnswer(questionIdAndAnswer, this.errorHandler);


        this.dataStore.set("answer", result);

        if (result) {
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