package com.example.iytechli.post.interfaces.http;

import com.example.iytechli.common.domain.http.ApiResponse;
import com.example.iytechli.post.application.PostService;
import com.example.iytechli.post.domain.model.http.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
//@CrossOrigin("http://localhost:3000")
@CrossOrigin("https://iytechli-front-end-v1.vercel.app")
public class PostController {

    private final PostService postService;

    @PostMapping("/all")
    public ResponseEntity<ApiResponse<Page<AllPostsResponse>>> getAllPosts(@RequestBody AllPostsRequest allPostsRequest) {
        return postService.getAllPost(allPostsRequest.getPageNo(), allPostsRequest.getPageSize() , allPostsRequest.getUserId());
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<String>> createPost(@RequestBody CreatePostRequest createPostRequest) throws Exception {
        return postService.createPost(createPostRequest);
    }

    @PostMapping("/like")
    public ResponseEntity<ApiResponse<String>> likePost(@RequestBody LikePostRequest likePostRequest)  throws Exception{
        return postService.likePost(likePostRequest);
    }

    @GetMapping("/post-detail/{postId}/{userId}")
    public ResponseEntity<ApiResponse<PostDetailResponse>> getPostDetailByPostId(@PathVariable("postId") String postId,
                                                                                 @PathVariable("userId") String userId) throws Exception {
        return postService.getPostDetailByPostId(postId,userId);
    }
}
