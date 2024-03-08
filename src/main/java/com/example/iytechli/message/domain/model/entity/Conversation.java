package com.example.iytechli.message.domain.model.entity;

import com.example.iytechli.user.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Document(value = "conversations")
@Data
@AllArgsConstructor
@Builder
public class Conversation {
    @Id
    private String id;

    @DBRef
    // TODO Check that the size of participants
    private List<User> participants;

    private String lastMessage;
    private Date lastMessageTime;
}
