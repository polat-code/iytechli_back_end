package com.example.iytechli.post.interfaces.http;

import com.example.iytechli.post.application.PostService;
import com.example.iytechli.post.domain.model.http.AllPostsRequest;
import com.example.iytechli.post.domain.model.http.AllPostsResponse;
import com.example.iytechli.post.domain.model.http.CreatePostRequest;
import com.example.iytechli.post.domain.model.http.LikePostRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {

    private final PostService postService;

    @GetMapping("/all")
    public ResponseEntity<Page<AllPostsResponse>> getAllPosts(@RequestBody AllPostsRequest allPostsRequest) {
        return postService.getAllPost(allPostsRequest.getPageNo(), allPostsRequest.getPageSize() , allPostsRequest.getUserId());
    }

    @PostMapping("")
    public ResponseEntity<String> createPost(@RequestBody CreatePostRequest createPostRequest) throws Exception {
        return postService.createPost(createPostRequest);
    }

    @PostMapping("/like")
    public ResponseEntity<String> likePost(@RequestBody LikePostRequest likePostRequest)  throws Exception{
        return postService.likePost(likePostRequest);
    }

}
