package com.example.iytechli.comment.domain.model.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AllCommentsByPostResponse {

    private String commentId;
    private String commentContent;
    private boolean isUserLikeComment;
    private boolean isUserDislikeComment;
    private Date isCreatedAt;
    private String commentOwnerName;
    private String commentOwnerSurname;
    private String commentOwnerUserId;
    private int numberOfLikes;
    private int numberOfDislikes;
}
