package com.example.iytechli.comment.interfaces.http;

import com.example.iytechli.comment.application.CommentService;
import com.example.iytechli.comment.domain.model.http.*;
import com.example.iytechli.common.domain.http.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
@CrossOrigin("http://localhost:3000")
public class CommentController {

    private final CommentService commentService;
    @PostMapping("")
    public ResponseEntity<ApiResponse<String>> createComment(@RequestBody CreateCommentRequest createCommentRequest) throws Exception {
        return commentService.createComment(createCommentRequest);
    }

    @PostMapping("/all")
    public ResponseEntity<ApiResponse<List<AllCommentsByPostResponse>>> getAllCommentsByPost(@RequestBody AllCommentsByPostRequest allCommentsByPostRequest) throws Exception {
        return commentService.getAllCommentsByPost(allCommentsByPostRequest);
    }

    @PostMapping("/like")
    public ResponseEntity<ApiResponse<String>> likeComment(@RequestBody LikeCommentRequest likeCommentRequest) throws Exception {
        return commentService.likeComment(likeCommentRequest);
    }

    @PostMapping("/dislike")
    public ResponseEntity<String> dislikeComment(@RequestBody DislikeCommentRequest dislikeCommentRequest) throws Exception {
        return commentService.dislikeComment(dislikeCommentRequest);
    }
 }
