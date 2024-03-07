package com.example.iytechli.message.domain.model.entity;

import com.example.iytechli.user.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(value = "message_detail")
@Data
@AllArgsConstructor
@Builder
public class MessageDetail {

    @Id
    private String id;

    @DBRef
    private User clientUser;

    @DBRef
    private User crossClientUser;

    private List<Message> messages;
    private Date createdAt;
}
