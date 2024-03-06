package com.example.iytechli.security.authentication.domain.model.http;

public class OtpNotValidException extends Exception{
    public OtpNotValidException() {
    }

    public OtpNotValidException(String message) {
        super(message);
    }
}
