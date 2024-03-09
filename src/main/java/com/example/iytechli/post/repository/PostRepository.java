package com.example.iytechli.post.repository;

import com.example.iytechli.post.domain.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<Post,String> {
    Page<Post> findByIsActivePostTrue(Pageable pageable);
}
