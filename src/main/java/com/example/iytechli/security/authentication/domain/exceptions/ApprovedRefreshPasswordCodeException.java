package com.example.iytechli.security.authentication.domain.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class ApprovedRefreshPasswordCodeException extends Exception{
    public ApprovedRefreshPasswordCodeException() {
        super("Approved Refresh Code is not valid");
    }

    public ApprovedRefreshPasswordCodeException(String message) {
        super(message);
    }
}
