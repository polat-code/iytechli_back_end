package com.example.iytechli.compliment.domain.model.entity;

import com.example.iytechli.user.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(value = "compliment")
@Data
@AllArgsConstructor
@Builder
public class Compliment {

    @Id
    private String id;

    @DBRef
    private User user;

    private ReportReason reportReason;
    private String description;
    private Date createdAt;
}
