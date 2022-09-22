import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
/**
 * Logic needed for the view playlist page of the website.
 */
class LoginPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['login'], this);
        this.dataStore = new DataStore();

    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the question/answer list.
     */
    mount() {
        document.getElementById('get-userName-form').addEventListener('submit', this.login);

    }

    async login(event) {
        event.preventDefault();

        let userId = document.getElementById("login-userName");
        sessionStorage.setItem("userName", userId.value);

        if (sessionStorage.getItem("userName") != null) {
            this.showMessage(`Welcome ${sessionStorage.getItem("userName")}!`)
            window.location.href = "index.html";
        } else {
            this.errorHandler("Error logging in!  Try again...");
        }
    }

}
/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const loginPage = new LoginPage();

    if (sessionStorage.getItem("userName") != null){
        window.location.href = "index.html";
    }

    loginPage.mount();

};

window.addEventListener('DOMContentLoaded', main);