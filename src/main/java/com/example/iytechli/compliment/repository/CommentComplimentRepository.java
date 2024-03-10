package com.example.iytechli.compliment.repository;

import com.example.iytechli.compliment.domain.model.entity.CommentCompliment;
import com.example.iytechli.compliment.domain.model.entity.Compliment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentComplimentRepository extends MongoRepository<CommentCompliment,String> {
}
