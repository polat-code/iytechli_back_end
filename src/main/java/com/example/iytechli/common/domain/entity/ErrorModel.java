package com.example.iytechli.common.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ErrorModel {

    private String message;
    private int errorCode;
}
