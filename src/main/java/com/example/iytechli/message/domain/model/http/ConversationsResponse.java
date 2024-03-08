package com.example.iytechli.message.domain.model.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConversationsResponse {

    private String conversationId;
    private String crossClientName;
    private String crossClientSurname;
    private String lastMessage;
    private Date lastMessageTime;
}
