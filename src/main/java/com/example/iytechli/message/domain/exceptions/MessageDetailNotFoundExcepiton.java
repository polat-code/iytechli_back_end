package com.example.iytechli.message.domain.exceptions;

public class MessageDetailNotFoundExcepiton extends Exception{
    public MessageDetailNotFoundExcepiton() {
        super("Message Detail is not found");
    }

    public MessageDetailNotFoundExcepiton(String message) {
        super(message);
    }
}
