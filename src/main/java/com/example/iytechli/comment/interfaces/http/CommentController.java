package com.example.iytechli.comment.interfaces.http;

import com.example.iytechli.comment.application.CommentService;
import com.example.iytechli.comment.domain.model.http.AllCommentsByPostRequest;
import com.example.iytechli.comment.domain.model.http.AllCommentsByPostResponse;
import com.example.iytechli.comment.domain.model.http.CreateCommentRequest;
import com.example.iytechli.comment.domain.model.http.LikeCommentRequest;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;
    @PostMapping("")
    public ResponseEntity<String> createComment(@RequestBody CreateCommentRequest createCommentRequest) throws Exception {
        return commentService.createComment(createCommentRequest);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AllCommentsByPostResponse>> getAllCommentsByPost(@RequestBody AllCommentsByPostRequest allCommentsByPostRequest) throws Exception {
        return commentService.getAllCommentsByPost(allCommentsByPostRequest);
    }

    @PostMapping("/like")
    public ResponseEntity<String> likeComment(@RequestBody LikeCommentRequest likeCommentRequest) throws Exception {
        return commentService.likeComment(likeCommentRequest);
    }
}
