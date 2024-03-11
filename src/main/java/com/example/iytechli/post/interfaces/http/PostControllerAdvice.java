package com.example.iytechli.post.interfaces.http;

import com.example.iytechli.comment.domain.exceptions.CommentNotFoundException;
import com.example.iytechli.common.domain.entity.ErrorCodes;
import com.example.iytechli.common.domain.entity.ErrorModel;
import com.example.iytechli.common.domain.http.ApiResponse;
import com.example.iytechli.post.domain.exceptions.PostNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class PostControllerAdvice {

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> postNotFoundException(PostNotFoundException ex, WebRequest webRequest){
        return new ResponseEntity<>(new ApiResponse<>("Error",ex.getMessage(), ErrorCodes.NOT_FOUND.getErrorCode(),false, new Date()), HttpStatus.OK);
    }
}
