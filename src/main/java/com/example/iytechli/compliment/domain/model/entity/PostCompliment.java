package com.example.iytechli.compliment.domain.model.entity;

import com.example.iytechli.post.domain.model.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Post_Compliment")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostCompliment extends Compliment {


    @DBRef
    private Post post;
}
