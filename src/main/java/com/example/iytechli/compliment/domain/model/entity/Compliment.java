package com.example.iytechli.compliment.domain.model.entity;

import com.example.iytechli.user.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Compliment {

    @Id
    private String id;

    @DBRef
    private User user;

    private ReportReason reportReason;
    private String description;
    private Date createdAt;

}
