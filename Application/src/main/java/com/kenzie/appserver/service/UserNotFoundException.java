package com.kenzie.appserver.service;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message){
        super(message);}
}
