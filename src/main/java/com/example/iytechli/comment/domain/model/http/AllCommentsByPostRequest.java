package com.example.iytechli.comment.domain.model.http;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AllCommentsByPostRequest {

        private String postId;
        private String userId;
}
