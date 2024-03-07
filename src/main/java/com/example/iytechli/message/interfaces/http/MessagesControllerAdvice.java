package com.example.iytechli.message.interfaces.http;

import com.example.iytechli.common.domain.entity.ErrorCodes;
import com.example.iytechli.common.domain.entity.ErrorModel;
import com.example.iytechli.message.domain.exceptions.MessageDetailNotFoundException;
import com.example.iytechli.security.authentication.domain.exceptions.AlreadyRegisteredUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class MessagesControllerAdvice {
    @ExceptionHandler(MessageDetailNotFoundException.class)
    public ResponseEntity<ErrorModel> messageDetailNotFoundException(MessageDetailNotFoundException ex, WebRequest webRequest){
        return new ResponseEntity<>(new ErrorModel(ex.getMessage(), ErrorCodes.NOT_FOUND.getErrorCode()), HttpStatus.NOT_FOUND);
    }

}
