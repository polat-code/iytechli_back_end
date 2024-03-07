package com.example.iytechli.message.domain.exceptions;

public class MessageDetailNotFoundException extends Exception{
    public MessageDetailNotFoundException() {
        super("Message detail not found");
    }

    public MessageDetailNotFoundException(String message) {
        super(message);
    }
}
