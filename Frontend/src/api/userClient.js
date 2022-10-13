import BaseClass from "../util/baseClass";
import axios from 'axios'

export default class UserClient extends BaseClass {

    constructor(props = {}){
        super();
        const methodsToBind = ['clientLoaded', 'getUserById', 'getRandomQuestion', 'submitAnswer'];
        this.bindClassMethods(methodsToBind, this);
        this.props = props;
        this.clientLoaded(axios);
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     * @param client The client that has been successfully loaded.
     */
    clientLoaded(client) {
        this.client = client;
        if (this.props.hasOwnProperty("onReady")){
            this.props.onReady();
        }
    }

    async getRandomQuestion(errorCallback){
        try{
            const  response = await this.client.get(`/category/random`);
                return response.data; // this should return question
        }catch (error){
            this.handleError("getRandomQuestion", error, errorCallback)
        }
    }

    async submitAnswer(userInput, errorCallback){
       const inputArray = userInput.split(",");
        const questionId = inputArray[0];
        const userAnswer = inputArray[1];
        try{
            const  response = await this.client.post(`/category/submitAnswer?answer=${userAnswer}&questionId=${questionId}`, {
                "answer": userAnswer,
                "questionId": questionId
            });

            return response.data;

        }catch (error){
            this.handleError("submitAnswer", error, errorCallback)
        }
    }



    /**
     * This is a test
     * Gets the user points for the given userId.
     * @param userId String identifier for a user
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The concert
     * test userid == 5b288a6a-355e-11ed-a261-0242ac120002
     * * *
     */
    async getUserById(userId, errorCallback) {
        try {

            const response = await this.client.get(`/user/${userId}`, {
                "userId": userId
            });
            console.log(response);
            return response.data; // this should return user
        } catch (error) {
            this.handleError("getUserByID", error, errorCallback)
        }
    }

    // async createExample(name, errorCallback) {
    //     try {
    //         const response = await this.client.post(`example`, {
    //             name: name
    //         });
    //         return response.data;
    //     } catch (error) {
    //         this.handleError("createExample", error, errorCallback);
    //     }
    // }

    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(method, error, errorCallback) {
        console.error(method + " failed - " + error);
        if (error.response.data.message !== undefined) {
            console.error(error.response.data.message);
        }
        if (errorCallback) {
            errorCallback(method + " failed - " + error);
        }
    }
}