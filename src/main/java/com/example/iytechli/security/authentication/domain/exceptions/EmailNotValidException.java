package com.example.iytechli.security.authentication.domain.exceptions;

public class EmailNotValidException extends Exception{
    public EmailNotValidException() {
        super("Invalid Email");
    }

    public EmailNotValidException(String message) {
        super(message);
    }
}
