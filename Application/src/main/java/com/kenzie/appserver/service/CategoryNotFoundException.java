package com.kenzie.appserver.service;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String message){
        super(message);}
}
