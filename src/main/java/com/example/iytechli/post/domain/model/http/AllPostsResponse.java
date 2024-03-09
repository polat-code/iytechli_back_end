package com.example.iytechli.post.domain.model.http;

import com.example.iytechli.post.domain.model.entity.Photo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AllPostsResponse {

    private String postId;
    private String content;
    private List<Photo> photoList;
    private int numberOfComments;
    private int numberOfLikes;
    private Date createdAt;
}
