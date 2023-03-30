package com.five.spring_demo.common;

public class CustomException extends RuntimeException{
    public CustomException(String message) {
        super(message);
    }
}
