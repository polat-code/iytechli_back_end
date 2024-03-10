package com.example.iytechli.comment.interfaces.http;

import com.example.iytechli.comment.domain.exceptions.CommentNotFoundException;
import com.example.iytechli.common.domain.entity.ErrorCodes;
import com.example.iytechli.common.domain.entity.ErrorModel;
import com.example.iytechli.message.domain.exceptions.MessageDetailNotFoundExcepiton;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CommentControllerAdvice {

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorModel> commentNotFoundException(CommentNotFoundException ex, WebRequest webRequest){
        return new ResponseEntity<>(new ErrorModel(ex.getMessage(), ErrorCodes.NOT_FOUND.getErrorCode()), HttpStatus.NOT_FOUND);
    }

}
