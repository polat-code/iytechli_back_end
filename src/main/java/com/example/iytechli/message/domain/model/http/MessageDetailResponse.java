package com.example.iytechli.message.domain.model.http;

import com.example.iytechli.message.domain.model.entity.UserMessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDetailResponse {

    private String content;
    private UserMessageType messageOwner;
    private Date sentAt;
}
