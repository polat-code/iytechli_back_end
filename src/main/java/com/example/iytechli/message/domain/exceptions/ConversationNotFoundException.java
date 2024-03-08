package com.example.iytechli.message.domain.exceptions;

public class ConversationNotFoundException extends Exception {
    public ConversationNotFoundException() {
        super("There is no such a conversation");
    }

    public ConversationNotFoundException(String message) {
        super(message);
    }
}
