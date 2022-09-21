import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import UserClient from "../api/userClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class BrainTeaser extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onGetPoints', 'renderExample', 'onGetQuestions'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        // document.getElementById('get-by-id-form').addEventListener('submit', this.onGet);
        // document.getElementById('create-form').addEventListener('submit', this.onCreate);
        // Renee testing
        document.getElementById('get-user-points-by-username-form').addEventListener('submit', this.onGetPoints);
        document.getElementById('get-all-questions-form').addEventListener('submit', this.onGetAllQuestions);

        this.client = new UserClient();

        this.dataStore.addChangeListener(this.renderExample)
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderExample() {
        let resultArea = document.getElementById("result-info");

        const user = this.dataStore.get("user");

        if (users) {
            resultArea.innerHTML = `
                <div>ID: ${user.userId}</div>
                <div>Name: ${user.points}</div>
            `
        } else {
            resultArea.innerHTML = "No Item";
        }
    }

    // Event Handlers --------------------------------------------------------------------------------------------------

async onGetAllQuestions(event){
        event.preventDefault();

        let result = await this.client.getAllQuestions(this.errorHandler);
        this.dataStore.set("question", result);

}


    async onGetPoints(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let userId = document.getElementById("userid-field").value;
        this.dataStore.set("user", null);

        let result = await this.client.getUserPointsById(userId, this.errorHandler);
        //this.dataStore.set("user", result);
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
    brainTeaser.mount();
};

window.addEventListener('DOMContentLoaded', main);