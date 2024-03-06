package com.example.iytechli.message.domain.model.entity;

import com.example.iytechli.user.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
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

    private User clientUser;
    private User crossClientUser;
    private List<Message> messageDetails;
    private Date createdAt;
}
