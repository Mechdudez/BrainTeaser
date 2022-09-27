import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import UserClient from "../api/userClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class BrainTeaser extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onGetUser', 'onGetQuestion',
        'renderQuestions'], this);
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
        document.getElementById('get-one-question-form').addEventListener('submit', this.onGetQuestion);

        this.client = new UserClient();

        this.dataStore.addChangeListener(this.renderQuestions)
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderQuestions(question) {
        let resultArea = document.getElementById("result-info");

        let storeHtmlQuestions = "";

        if (question != null) {
            storeHtmlQuestions += `<ul>`;
            storeHtmlQuestions += `<p><h3 class="listName" style="color:red;">${question.questionId}</h3></p>`;
            storeHtmlQuestions += `<p><b>Category: </b>${question.questions}</p>`;
            resultArea.innerHTML = storeHtmlQuestions;
        } else {
            resultArea.innerHTML = "No Item";
        }

    }

    // async renderExample() {
    //     let resultArea = document.getElementById("result-info");
    //
    //     const user = this.dataStore.get("user");
    //
    //     if (user) {
    //         resultArea.innerHTML = `
    //             <div>ID: ${user.userId}</div>
    //             <div>Name: ${user.points}</div>
    //         `
    //     } else {
    //         resultArea.innerHTML = "No Item";
    //     }
    // }

    // Event Handlers --------------------------------------------------------------------------------------------------


   async onGetQuestion(event) {
       event.preventDefault();

       let id = document.getElementById("question-field").value;
       this.dataStore.set("question", null);

       let result = await this.client.getQuestionById(id, this.errorHandler);
       this.dataStore.set("question", result);
       if (result) {
           this.showMessage(`Got ${result.id}!`)
       } else {
           this.errorHandler("Error doing GET!  Try again...");
       }


       // this.dataStore.set("question", null);
       //
       // let questionId = document.getElementById("question-field").value;
       //  let result = await this.client.getByQuestionId(questionId, this.errorHandler);
       //
       //
       //  this.dataStore.set("question", result);
       //
       // if (result) {
       //     this.showMessage(`Your new question is ${result}!`)
       // } else {
       //     this.errorHandler("Error fetching your question!  Try" +
       //         " again...");
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