package com.example.iytechli.compliment.interfaces.http;

import com.example.iytechli.common.domain.http.ApiResponse;
import com.example.iytechli.compliment.application.CommentComplimentService;
import com.example.iytechli.compliment.application.PostComplimentService;
import com.example.iytechli.compliment.domain.model.http.CreateCommentCompliment;
import com.example.iytechli.compliment.domain.model.http.CreatePostComplimentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/compliment")
@RequiredArgsConstructor
//@CrossOrigin("http://localhost:3000")
@CrossOrigin("thawing-boar-fynrgkl61w2eug9dcgolkj2f.herokudns.com")
public class ComplimentController {

    private final PostComplimentService postComplimentService;
    private final CommentComplimentService commentComplimentService;

    @PostMapping("/post")
    public ResponseEntity<ApiResponse<String>> createPostCompliment(@RequestBody CreatePostComplimentRequest createPostComplimentRequest) throws Exception {
        return postComplimentService.createPostComplimentObject(createPostComplimentRequest);
    }

    @PostMapping("/comment")
    public ResponseEntity<ApiResponse<String>> createCommentCompliment(@RequestBody CreateCommentCompliment createCommentCompliment) throws Exception {
        return commentComplimentService.createCommentCompliment(createCommentCompliment);
    }
}
