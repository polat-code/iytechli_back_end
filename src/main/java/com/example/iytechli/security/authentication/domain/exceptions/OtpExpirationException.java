package com.example.iytechli.security.authentication.domain.exceptions;

public class OtpExpirationException extends Exception{
    public OtpExpirationException() {
    }

    public OtpExpirationException(String message) {
        super(message);
    }
}
