package com.example.iytechli.message.domain.model.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDetailRequest {

    private String conversationId;
    private String clientUserId;
}
