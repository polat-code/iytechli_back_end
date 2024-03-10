package com.example.iytechli.compliment.repository;

import com.example.iytechli.compliment.domain.model.entity.PostCompliment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostComplimentRepository extends MongoRepository<PostCompliment,String> {
}
