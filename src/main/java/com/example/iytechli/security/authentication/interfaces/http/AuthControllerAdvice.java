package com.example.iytechli.security.authentication.interfaces.http;

import com.example.iytechli.common.domain.entity.ErrorModel;
import com.example.iytechli.common.exception.EmailCannotSentException;
import com.example.iytechli.security.authentication.domain.exceptions.AlreadyRegisteredUser;
import com.example.iytechli.security.authentication.domain.exceptions.EmailNotValidException;
import com.example.iytechli.security.authentication.domain.exceptions.OtpExpirationException;
import com.example.iytechli.security.authentication.domain.model.http.OtpNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler(AlreadyRegisteredUser.class)
    public ResponseEntity<ErrorModel> alreadyRegisteredUserException(AlreadyRegisteredUser ex, WebRequest webRequest){
        return new ResponseEntity<>(new ErrorModel(ex.getMessage(),409), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(EmailNotValidException.class)
    public ResponseEntity<ErrorModel> emailNotValidException(EmailNotValidException ex, WebRequest webRequest){
        return new ResponseEntity<>(new ErrorModel(ex.getMessage(),HttpStatus.NOT_ACCEPTABLE.value()), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(OtpExpirationException.class)
    public ResponseEntity<ErrorModel> otpExpirationException(OtpExpirationException ex, WebRequest webRequest){
        return new ResponseEntity<>(new ErrorModel(ex.getMessage(),HttpStatus.NOT_ACCEPTABLE.value()), HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorModel> usernameNotFoundException(UsernameNotFoundException ex, WebRequest webRequest){
        return new ResponseEntity<>(new ErrorModel(ex.getMessage(),HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(EmailCannotSentException.class)
    public ResponseEntity<ErrorModel> emailCannotSentException(EmailCannotSentException ex, WebRequest webRequest){
        return new ResponseEntity<>(new ErrorModel(ex.getMessage(),HttpStatus.NOT_ACCEPTABLE.value()), HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(OtpNotValidException.class)
    public ResponseEntity<ErrorModel> otpNotValidException(OtpNotValidException ex, WebRequest webRequest){
        return new ResponseEntity<>(new ErrorModel(ex.getMessage(),HttpStatus.NOT_ACCEPTABLE.value()), HttpStatus.NOT_ACCEPTABLE);
    }


}
