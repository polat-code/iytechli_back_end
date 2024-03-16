package com.example.iytechli.post.application;

import com.example.iytechli.common.domain.http.ApiResponse;
import com.example.iytechli.post.domain.exceptions.PostNotFoundException;
import com.example.iytechli.post.domain.model.entity.Photo;
import com.example.iytechli.post.domain.model.entity.Post;
import com.example.iytechli.post.domain.model.http.*;
import com.example.iytechli.post.repository.PostRepository;
import com.example.iytechli.user.application.UserService;
import com.example.iytechli.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public ResponseEntity<ApiResponse<Page<AllPostsResponse>>> getAllPost(int pageNo, int pageSize, String userId) {
        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by("createdAt").descending());
        Page<Post> pagePosts =  postRepository.findByIsActivePostTrue(pageable);

        Optional<User> optionalUser = userService.findUserById(userId);
        if(optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("There is no such a userId " + userId);
        }

        List<AllPostsResponse> allPostsResponseList = pagePosts.getContent()
                .stream()
                .map(post -> convertToAllPostsResponse(post,optionalUser.get()))
                .collect(Collectors.toList());

        Page<AllPostsResponse> allPostsResponsesPage = new PageImpl<>(allPostsResponseList,pageable,pagePosts.getTotalPages());

        return new ResponseEntity<>(new ApiResponse<>(allPostsResponsesPage,"Posts are sent successfully",200,true,new Date()) ,HttpStatus.OK);
    }

    private AllPostsResponse convertToAllPostsResponse(Post post,User user) {
        return AllPostsResponse.builder()
                .postId(post.getId())
                .content(post.getContent())
                // TODO Test isUserLikes
                .isCurrentUserLikePost(post.getLikes().contains(user))
                .photoList(post.getPhotos())
                .numberOfComments(post.getComments().size())
                .numberOfLikes(post.getLikes().size())
                .createdAt(post.getCreatedAt())
                .build();
    }
    // TODO Assign isActivePost to a variable to change sometimes
    public ResponseEntity<ApiResponse<String>> createPost(CreatePostRequest createPostRequest) throws Exception {
        Optional<User> optionalUser = userService.findUserById(createPostRequest.getContentOwnerUserId());
        if(optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("There is not such a content owner " + createPostRequest.getContentOwnerUserId());
        }
        List<Photo> photos = new ArrayList<>();
        for(CreatePostPhotoRequest createPostPhotoRequest : createPostRequest.getPhotoList()) {
            if(createPostPhotoRequest.getImage() == null) {
                continue;
            }
            Photo photo = Photo.builder()
                    .image(createPostPhotoRequest.getImage())
                    .createdAt(new Date())
                    .build();
            photos.add(photo);
        }

        Post post = Post.builder()
                .user(optionalUser.get())
                .content(createPostRequest.getContent())
                .photos(photos)
                .noteToAdmin(createPostRequest.getNoteToAdmin())
                // TODO Assign isActivePost a variable to share posts automatically in the system
                .isActivePost(true)
                .createdAt(new Date())
                .comments(new ArrayList<>())
                .compliments(new ArrayList<>())
                .likes(new ArrayList<>())
                .build();
        postRepository.save(post);
        return new ResponseEntity<>(new ApiResponse<>("Post is saved successfully","",200,true,new Date()), HttpStatus.OK);

    }

    public ResponseEntity<ApiResponse<String>> likePost(LikePostRequest likePostRequest) throws Exception {
        Optional<Post> optionalPost = postRepository.findById(likePostRequest.getPostId());
        if(optionalPost.isEmpty()) {
            throw new PostNotFoundException("There is no such a post " + likePostRequest.getPostId());
        }
        Optional<User> optionalUser = userService.findUserById(likePostRequest.getUserId());
        if(optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("There is no scuh a userId: " + likePostRequest.getUserId());
        }
        Post post = optionalPost.get();
        User user = optionalUser.get();
        if(post.getLikes().contains(user)) {
            post.getLikes().remove(user);
        }else {
            post.getLikes().add(user);
        }
        postRepository.save(post);
        return new ResponseEntity<>(new ApiResponse<>("Like is successful","",200,true,new Date()),HttpStatus.OK);
    }

    public Optional<Post> findPostById(String postId) {
        return postRepository.findById(postId);
    }

    public void savePost(Post post) {
        postRepository.save(post);
    }


    private User checkUser(String userId) throws Exception {
        Optional<User> optionalUser = userService.findUserById(userId);
        if(optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("There is no such userId " + userId);
        }
        return optionalUser.get();
    }
    private Post checkPost(String postId) throws Exception {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if(optionalPost.isEmpty()) {
            throw new PostNotFoundException("There is no such postId " + postId);
        }
        return optionalPost.get();
    }
}
