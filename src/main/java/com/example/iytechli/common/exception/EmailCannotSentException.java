package com.example.iytechli.common.exception;

public class EmailCannotSentException extends Exception{

    public EmailCannotSentException() {
        super("Email Cannot be sent");
    }

    public EmailCannotSentException(String message) {
        super(message);
    }
}
