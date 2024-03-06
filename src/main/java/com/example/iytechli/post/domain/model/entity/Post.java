package com.example.iytechli.post.domain.model.entity;

import com.example.iytechli.comment.domain.model.entity.Comment;
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

@Document(value = "Post")
@Data
@AllArgsConstructor
@Builder
public class Post {

    @Id
    private String id;
    private String content;
    @DBRef
    private User user;

    private List<Photo> photos;

    @DBRef
    private List<Comment> comments;

    @DBRef
    private List<User> likes;

    @DBRef
    private List<Compliment> compliments;

    private boolean isActivePost;
    private Date createdAt;

}
