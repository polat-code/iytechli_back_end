package com.example.iytechli.common.domain.entity;


public enum ErrorCodes {
    NOT_APPROVED(410),
    NOT_VALID(411),
    NOT_FOUND(404),
    EXPIRED(498),
    NOT_ACCEPTABLE(406),
    ALREADY_EXIST(409);



    private final int errorCode;

    ErrorCodes(int errorCode){
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}
