package com.example.iytechli.message.domain.model.entity;

import com.example.iytechli.user.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(value = "messages")
@Data
@AllArgsConstructor
@Builder
public class Message {
    @Id
    private String id;

    @DBRef
    private Conversation conversation;

    @DBRef
    private User senderUser;
    private String content;

    private String status;
    private Date createdAt;
}
