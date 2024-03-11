package com.example.iytechli.compliment.application;

import com.example.iytechli.compliment.domain.model.entity.PostCompliment;
import com.example.iytechli.compliment.domain.model.http.CreatePostComplimentRequest;
import com.example.iytechli.compliment.repository.PostComplimentRepository;
import com.example.iytechli.post.application.PostService;
import com.example.iytechli.post.domain.exceptions.PostNotFoundException;
import com.example.iytechli.post.domain.model.entity.Post;
import com.example.iytechli.user.application.UserService;
import com.example.iytechli.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostComplimentService {

    private final PostComplimentRepository postComplimentRepository;
    private final UserService userService;
    private final PostService postService;

    // TODO Compliment service lerini ayır.
    public ResponseEntity<String> createPostComplimentObject(
            CreatePostComplimentRequest createPostComplimentRequest) throws Exception
    {
        User user = checkUser(createPostComplimentRequest.getUserId());
        Post post = checkPost(createPostComplimentRequest.getPostId());

        PostCompliment postCompliment = createPostComplimentObject(post,user,createPostComplimentRequest);

        postComplimentRepository.save(postCompliment);

        return new ResponseEntity<>("Comment Compliment is added", HttpStatus.OK);

    }

    private PostCompliment createPostComplimentObject(Post post, User user, CreatePostComplimentRequest createPostComplimentRequest) {
        PostCompliment postCompliment = new PostCompliment();
        postCompliment.setDescription(createPostComplimentRequest.getDescription());
        postCompliment.setReportReason(createPostComplimentRequest.getReportReason());
        postCompliment.setPost(post);
        postCompliment.setUser(user);
        postCompliment.setCreatedAt(new Date());
        return postCompliment;
    }

    private Post checkPost(String postId) throws Exception {
        Optional<Post> optionalPost = postService.findPostById(postId);
        if(optionalPost.isEmpty()) {
            throw new PostNotFoundException("There is no such postId : " + postId);
        }
        return optionalPost.get();
    }

    private User checkUser(String userId) throws Exception {
        Optional<User> optionalUser = userService.findUserById(userId);
        if(optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("There is no such userId : " + userId);
        }
        return optionalUser.get();
    }

}
