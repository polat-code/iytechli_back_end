package com.example.iytechli.user.repository;

import com.example.iytechli.user.domain.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,String> {

    User findByEmail(String email);

    @Query("{'email': ?0}")
    Optional<User> findByAlreadyEmail(String email);
}
