package com.example.iytechli.security.authentication.interfaces.http;

import com.example.iytechli.common.domain.entity.ErrorCodes;
import com.example.iytechli.common.domain.entity.ErrorModel;
import com.example.iytechli.common.domain.http.ApiResponse;
import com.example.iytechli.common.exception.EmailCannotSentException;
import com.example.iytechli.security.authentication.domain.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class AuthControllerAdvice {


    @ExceptionHandler(AlreadyRegisteredUser.class)
    public ResponseEntity<ApiResponse<String>> alreadyRegisteredUserException(AlreadyRegisteredUser ex, WebRequest webRequest){
        return new ResponseEntity<>(new ApiResponse<>("Error",ex.getMessage(), ErrorCodes.ALREADY_EXIST.getErrorCode(),false, new Date()), HttpStatus.OK);
    }

    @ExceptionHandler(EmailNotValidException.class)
    public ResponseEntity<ApiResponse<String>> emailNotValidException(EmailNotValidException ex, WebRequest webRequest){
        return new ResponseEntity<>(new ApiResponse<>("Error",ex.getMessage(), ErrorCodes.NOT_VALID.getErrorCode(),false, new Date()), HttpStatus.OK);
    }

    @ExceptionHandler(OtpExpirationException.class)
    public ResponseEntity<ApiResponse<String>> otpExpirationException(OtpExpirationException ex, WebRequest webRequest){
        return new ResponseEntity<>(new ApiResponse<>("Error",ex.getMessage(), ErrorCodes.EXPIRED.getErrorCode(),false, new Date()),HttpStatus.OK);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> usernameNotFoundException(UsernameNotFoundException ex, WebRequest webRequest){
        return new ResponseEntity<>(new ApiResponse<>("Error",ex.getMessage(), ErrorCodes.NOT_FOUND.getErrorCode(),false, new Date()), HttpStatus.OK);
    }
    @ExceptionHandler(EmailCannotSentException.class)
    public ResponseEntity<ApiResponse<String>> emailCannotSentException(EmailCannotSentException ex, WebRequest webRequest){
        return new ResponseEntity<>(new ApiResponse<>("Error",ex.getMessage(), ErrorCodes.NOT_ACCEPTABLE.getErrorCode(),false, new Date()), HttpStatus.OK);
    }
    @ExceptionHandler(OtpNotValidException.class)
    public ResponseEntity<ApiResponse<String>> otpNotValidException(OtpNotValidException ex, WebRequest webRequest){
        return new ResponseEntity<>(new ApiResponse<>("Error",ex.getMessage(), ErrorCodes.NOT_VALID.getErrorCode(),false, new Date()), HttpStatus.OK);
    }
    @ExceptionHandler(OtpNotApprovedException.class)
    public ResponseEntity<ApiResponse<String>> otpNotApprovedException(OtpNotApprovedException ex, WebRequest webRequest){
        return new ResponseEntity<>(new ApiResponse<>("Error",ex.getMessage(), ErrorCodes.NOT_APPROVED.getErrorCode(),false, new Date()), HttpStatus.OK);
    }
    @ExceptionHandler(RefreshPasswordCodeInvalidException.class)
    public ResponseEntity<ApiResponse<String>> refreshPasswordCodeInvalidException(RefreshPasswordCodeInvalidException ex, WebRequest webRequest){
        return new ResponseEntity<>(new ApiResponse<>("Error",ex.getMessage(), ErrorCodes.NOT_APPROVED.getErrorCode(),false, new Date()), HttpStatus.OK);
    }
    @ExceptionHandler(ApprovedRefreshPasswordCodeException.class)
    public ResponseEntity<ApiResponse<String>> approvedRefreshPasswordCodeException(ApprovedRefreshPasswordCodeException ex, WebRequest webRequest){
        return new ResponseEntity<>(new ApiResponse<>("Error",ex.getMessage(), ErrorCodes.NOT_FOUND.getErrorCode(),false, new Date()), HttpStatus.OK);
    }





}
