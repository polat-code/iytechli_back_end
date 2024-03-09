package com.example.iytechli.post.application;

import com.example.iytechli.post.domain.model.entity.Photo;
import com.example.iytechli.post.domain.model.entity.Post;
import com.example.iytechli.post.domain.model.http.AllPostsRequest;
import com.example.iytechli.post.domain.model.http.AllPostsResponse;
import com.example.iytechli.post.domain.model.http.CreatePostPhotoRequest;
import com.example.iytechli.post.domain.model.http.CreatePostRequest;
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

    public ResponseEntity<Page<AllPostsResponse>> getAllPost(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by("createdAt").descending());
        Page<Post> pagePosts =  postRepository.findAll(pageable);

        List<AllPostsResponse> allPostsResponseList = pagePosts.getContent()
                .stream()
                .map(this::convertToAllPostsResponse)
                .collect(Collectors.toList());

        Page<AllPostsResponse> allPostsResponsesPage = new PageImpl<>(allPostsResponseList,pageable,pagePosts.getTotalPages());

        return new ResponseEntity<>(allPostsResponsesPage,HttpStatus.OK);
    }

    private AllPostsResponse convertToAllPostsResponse(Post post) {
        return AllPostsResponse.builder()
                .postId(post.getId())
                .content(post.getContent())
                .photoList(post.getPhotos())
                .numberOfComments(post.getComments().size())
                .numberOfLikes(post.getLikes().size())
                .createdAt(post.getCreatedAt())
                .build();
    }
    // TODO Assign isActivePost to a variable to change sometimes
    public ResponseEntity<String> createPost(CreatePostRequest createPostRequest) throws Exception {
        Optional<User> optionalUser = userService.findUserById(createPostRequest.getContentOwnerUserId());
        if(optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("There is not such a content owner " + createPostRequest.getContentOwnerUserId());
        }
        List<Photo> photos = new ArrayList<>();
        for(CreatePostPhotoRequest createPostPhotoRequest : createPostRequest.getPhotoList()) {
            Photo photo = Photo.builder()
                    .name(createPostPhotoRequest.getName())
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
                .isActivePost(false)
                .createdAt(new Date())
                .comments(new ArrayList<>())
                .compliments(new ArrayList<>())
                .likes(new ArrayList<>())
                .build();
        postRepository.save(post);
        return new ResponseEntity<>("Post is saved successfully", HttpStatus.OK);

    }
}
