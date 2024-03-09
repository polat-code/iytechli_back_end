package com.example.iytechli.comment.application;

import com.example.iytechli.comment.domain.model.entity.Comment;
import com.example.iytechli.comment.domain.model.http.CreateCommentRequest;
import com.example.iytechli.comment.repository.CommentRepository;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    public ResponseEntity<String> createComment(CreateCommentRequest createCommentRequest) throws Exception {
        Optional<User> optionalUser = userService.findUserById(createCommentRequest.getUserId());
        if(optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("There is no such a userId " + createCommentRequest.getUserId());
        }
        Optional<Post> optionalPost = postService.findPostById(createCommentRequest.getPostId());
        if(optionalPost.isEmpty()) {
            throw new PostNotFoundException("There is no such a postId" + createCommentRequest.getPostId());
        }

        Comment comment  = createCommentObject(optionalUser.get(),createCommentRequest.getCommentContent());
        comment = commentRepository.save(comment);

        // Add comment into post
        Post post = optionalPost.get();
        post.getComments().add(comment);
        postService.savePost(post);

        return new ResponseEntity<>("Comment is created successfully", HttpStatus.OK);
    }

    private Comment createCommentObject(User user, String commentContent) {
        return Comment.builder()
                .comment(commentContent)
                .user(user)
                .likes(new ArrayList<>())
                .dislikes(new ArrayList<>())
                .compliments(new ArrayList<>())
                .isCreatedAt(new Date())
                .isUpdatedAt(new Date())
                // TODO Create a variable for isActiveComment to change
                .isActiveComment(true)
                .build();
    }
}
