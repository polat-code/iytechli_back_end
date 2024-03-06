package com.example.iytechli.security.authentication.domain.exceptions;

public class OtpNotValidException extends Exception{
    public OtpNotValidException() {
    }

    public OtpNotValidException(String message) {
        super(message);
    }
}
