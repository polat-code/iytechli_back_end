package com.example.iytechli.post.domain.exceptions;


public class PostNotFoundException extends Exception{
    public PostNotFoundException() {
        super("Post not found");
    }

    public PostNotFoundException(String message) {
        super(message);
    }
}
