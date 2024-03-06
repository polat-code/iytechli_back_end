package com.example.iytechli.message.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class Message {

    private UserType userType;
    private String message;
    private boolean isRead;
    private Date createdAt;
}
