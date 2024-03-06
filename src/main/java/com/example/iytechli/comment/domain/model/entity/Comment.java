package com.example.iytechli.comment.domain.model.entity;

import com.example.iytechli.compliment.domain.model.entity.Compliment;
import com.example.iytechli.user.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(value = "comment")
@Data
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    private String id;
    private String comment;

    @DBRef
    private User user;

    @DBRef
    private List<User> likes;

    @DBRef
    private List<User> dislikes;

    @DBRef
    private List<Compliment> compliments;

    private Date isCreatedAt;

    private Date isUpdatedAt;

    private boolean isActiveComment;


}
