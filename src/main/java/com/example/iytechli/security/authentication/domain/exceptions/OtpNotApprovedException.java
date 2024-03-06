package com.example.iytechli.security.authentication.domain.exceptions;

public class OtpNotApprovedException extends Exception{
    public OtpNotApprovedException() {
    }

    public OtpNotApprovedException(String message) {
        super(message);
    }
}
