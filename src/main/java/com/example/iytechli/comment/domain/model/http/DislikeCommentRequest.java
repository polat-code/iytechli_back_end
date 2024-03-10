package com.example.iytechli.comment.domain.model.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DislikeCommentRequest {

    private String userId;
    private String commentId;
}
