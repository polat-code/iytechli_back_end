package com.example.iytechli.message.domain.model.entity;

import com.example.iytechli.user.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(value = "document")
@Data
@AllArgsConstructor
@Builder
public class Messages {

    @Id
    private String id;
    // Each should have only one Messages document
    @DBRef
    @Indexed
    private User user;

    @DBRef
    private List<MessageDetail> messages;
}
