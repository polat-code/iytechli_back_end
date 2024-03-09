package com.example.iytechli.comment.repository;

import com.example.iytechli.comment.domain.model.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository  extends MongoRepository<Comment,String> {

}
