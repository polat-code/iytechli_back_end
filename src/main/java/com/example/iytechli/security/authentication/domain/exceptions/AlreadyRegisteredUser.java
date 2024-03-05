package com.example.iytechli.security.authentication.domain.exceptions;

public class AlreadyRegisteredUser extends Exception{
    public AlreadyRegisteredUser() {
        super("Already Registered User");
    }

    public AlreadyRegisteredUser(String message) {
        super(message);
    }
}
