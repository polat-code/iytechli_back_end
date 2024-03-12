package com.example.iytechli.security.authentication.domain.exceptions;

public class RefreshPasswordCodeInvalidException extends Exception{
    public RefreshPasswordCodeInvalidException() {
        super("Invalid Refresh Password Code");
    }

    public RefreshPasswordCodeInvalidException(String message) {
        super(message);
    }
}
