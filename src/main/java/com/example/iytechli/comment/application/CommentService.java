package com.example.iytechli.comment.application;

import com.example.iytechli.comment.domain.exceptions.CommentNotFoundException;
import com.example.iytechli.comment.domain.model.entity.Comment;
import com.example.iytechli.comment.domain.model.http.AllCommentsByPostRequest;
import com.example.iytechli.comment.domain.model.http.AllCommentsByPostResponse;
import com.example.iytechli.comment.domain.model.http.CreateCommentRequest;
import com.example.iytechli.comment.domain.model.http.LikeCommentRequest;
import com.example.iytechli.comment.repository.CommentRepository;
import com.example.iytechli.post.application.PostService;
import com.example.iytechli.post.domain.exceptions.PostNotFoundException;
import com.example.iytechli.post.domain.model.entity.Post;
import com.example.iytechli.user.application.UserService;
import com.example.iytechli.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    public ResponseEntity<String> createComment(CreateCommentRequest createCommentRequest) throws Exception {
        Optional<User> optionalUser = checkUser(createCommentRequest.getUserId());
        Optional<Post> optionalPost = checkPost(createCommentRequest.getPostId());

        Comment comment  = createCommentObject(optionalUser.get(),createCommentRequest.getCommentContent());
        comment = commentRepository.save(comment);

        // Add comment into post
        Post post = optionalPost.get();
        post.getComments().add(comment);
        postService.savePost(post);

        return new ResponseEntity<>("Comment is created successfully", HttpStatus.OK);
    }

    private Optional<Post> checkPost(String postId) throws PostNotFoundException {
        Optional<Post> optionalPost = postService.findPostById(postId);
        if(optionalPost.isEmpty()) {
            throw new PostNotFoundException("There is no such a postId" + postId);
        }
        return optionalPost;
    }

    private Optional<User> checkUser(String userId) {
        Optional<User> optionalUser = userService.findUserById(userId);
        if(optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("There is no such a userId " + userId);
        }
        return optionalUser;
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

    public ResponseEntity<List<AllCommentsByPostResponse>> getAllCommentsByPost(AllCommentsByPostRequest allCommentsByPostRequest) throws Exception {
        Optional<User> optionalUser = checkUser(allCommentsByPostRequest.getUserId());
        Optional<Post> optionalPost = checkPost(allCommentsByPostRequest.getPostId());

        List<Comment> comments  = optionalPost.get().getComments();

        List<AllCommentsByPostResponse> allCommentsByPostResponseList = comments
                .stream()
                .map(comment -> mapToAllCommentsByPostResponse(comment,optionalUser.get()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(allCommentsByPostResponseList,HttpStatus.OK);
    }

    private AllCommentsByPostResponse mapToAllCommentsByPostResponse(Comment comment, User user) {
        return AllCommentsByPostResponse.builder()
                .commentId(comment.getId())
                .commentContent(comment.getComment())
                .isUserLikeComment(comment.getLikes().contains(user))
                .isUserDislikeComment(comment.getDislikes().contains(user))
                .numberOfLikes(comment.getLikes().size())
                .numberOfDislikes(comment.getDislikes().size())
                .isCreatedAt(comment.getIsCreatedAt())
                .commentOwnerName(user.getName())
                .commentOwnerSurname(user.getSurname())
                .commentOwnerUserId(user.getId())
                .build();
    }

    public ResponseEntity<String> likeComment(LikeCommentRequest likeCommentRequest) throws Exception {
        Optional<User> optionalUser = checkUser(likeCommentRequest.getUserId());
        Optional<Comment> optionalComment = checkComment(likeCommentRequest.getCommentId());

        User user = optionalUser.get();

        List<User> likes = optionalComment.get().getLikes();

        if(likes.contains(user)){
            likes.remove(user);
        }else {
            likes.add(user);
        }
        commentRepository.save(optionalComment.get());
        return new ResponseEntity<>("Like is successful",HttpStatus.OK);

    }


    private Optional<Comment> checkComment(String commentId) throws Exception{
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if(optionalComment.isEmpty()) {
            throw new CommentNotFoundException("There is no such commentId " + commentId);
        }
        return optionalComment;
    }
}
