package com.example.iytechli.comment.interfaces.http;

import com.example.iytechli.comment.application.CommentService;
import com.example.iytechli.comment.domain.model.http.CreateCommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;
    @PostMapping("")
    public ResponseEntity<String> createComment(@RequestBody CreateCommentRequest createCommentRequest) throws Exception {
        return commentService.createComment(createCommentRequest);
    }
}
