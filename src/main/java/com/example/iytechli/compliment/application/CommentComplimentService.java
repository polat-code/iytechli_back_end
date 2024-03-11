package com.example.iytechli.compliment.application;

import com.example.iytechli.comment.application.CommentService;
import com.example.iytechli.comment.domain.exceptions.CommentNotFoundException;
import com.example.iytechli.comment.domain.model.entity.Comment;
import com.example.iytechli.common.domain.http.ApiResponse;
import com.example.iytechli.compliment.domain.model.entity.CommentCompliment;
import com.example.iytechli.compliment.domain.model.entity.PostCompliment;
import com.example.iytechli.compliment.domain.model.http.CreateCommentCompliment;
import com.example.iytechli.compliment.domain.model.http.CreatePostComplimentRequest;
import com.example.iytechli.compliment.repository.CommentComplimentRepository;
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
public class CommentComplimentService {

    private final CommentComplimentRepository commentComplimentRepository;
    private final UserService userService;
    private final CommentService commentService;

    public ResponseEntity<ApiResponse<String>> createCommentCompliment(
            CreateCommentCompliment createCommentCompliment) throws Exception
    {
        User user = checkUser(createCommentCompliment.getUserId());
        Comment comment = checkComment(createCommentCompliment.getCommentId());

        CommentCompliment commentCompliment = createCommentComplimentObject(user,comment,createCommentCompliment);

        commentComplimentRepository.save(commentCompliment);

        return new ResponseEntity<>(new ApiResponse<>("Comment compliment is created","",200,true,new Date()),HttpStatus.OK);
    }

    private CommentCompliment createCommentComplimentObject(
            User user,
            Comment comment,
            CreateCommentCompliment createCommentCompliment)
    {
        CommentCompliment commentCompliment = new CommentCompliment();
        commentCompliment.setUser(user);
        commentCompliment.setCreatedAt(new Date());
        commentCompliment.setDescription(createCommentCompliment.getDescription());
        commentCompliment.setReportReason(createCommentCompliment.getReportReason());
        commentCompliment.setComment(comment);

        return commentCompliment;
    }

    private Comment checkComment(String commentId) throws Exception {
        Optional<Comment> optionalComment = commentService.getCommentById(commentId);
        if(optionalComment.isEmpty()) {
            throw new CommentNotFoundException("There is no such commentId : " + commentId);
        }
        return optionalComment.get();
    }


    private User checkUser(String userId) throws Exception {
        Optional<User> optionalUser = userService.findUserById(userId);
        if(optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("There is no such userId : " + userId);
        }
        return optionalUser.get();
    }
}
