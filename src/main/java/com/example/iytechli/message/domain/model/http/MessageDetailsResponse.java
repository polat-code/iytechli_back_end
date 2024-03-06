package com.example.iytechli.message.domain.model.http;

import com.example.iytechli.message.domain.model.entity.UserType;
import com.example.iytechli.user.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class MessageDetailsResponse {

    private String messageDetailId;
    private User crossClientUser;
    private Date createdAt;
    private String lastMessage;
    private UserType lastMessageBy;

}
