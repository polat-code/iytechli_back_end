package com.example.iytechli.compliment.domain.model.entity;


import com.example.iytechli.comment.domain.model.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Comment_Compliment")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentCompliment extends Compliment {

    @DBRef
    private Comment comment;
}
