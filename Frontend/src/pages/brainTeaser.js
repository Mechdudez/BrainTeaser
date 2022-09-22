import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import UserClient from "../api/userClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class BrainTeaser extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onGetUser', 'onGetQuestions',
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
        document.getElementById('get-all-questions-form').addEventListener('submit', this.onGetAllQuestions);

        this.client = new UserClient();

        this.dataStore.addChangeListener(this.renderExample)
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderExample() {
        let resultArea = document.getElementById("result-info");

        const user = this.dataStore.get("user");

        if (user) {
            resultArea.innerHTML = `
                <div>ID: ${user.userId}</div>
                <div>Name: ${user.points}</div>
            `
        } else {
            resultArea.innerHTML = "No Item";
        }
    }

    // Event Handlers --------------------------------------------------------------------------------------------------


   async onGetQuestions(event){
        event.preventDefault();

        let result = await this.client.getAllQuestions(this.errorHandler);
        this.dataStore.set("question", result);
    }



    async onGetUser(event) {

        event.preventDefault();
        this.dataStore.set("user", null);
        let userId = document.getElementById("userid-field").value;


        let result = await this.client.getUserPointsById(userId, this.errorHandler);
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