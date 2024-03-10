package com.example.iytechli.comment.domain.exceptions;

public class CommentNotFoundException extends Exception{
    public CommentNotFoundException() {
        super("There is no such a comment");
    }

    public CommentNotFoundException(String message) {
        super(message);
    }
}
