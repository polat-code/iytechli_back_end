package com.example.iytechli.message.repository;

import com.example.iytechli.message.domain.model.entity.Messages;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagesRepository extends MongoRepository<Messages,String> {

    Messages findByUser_Id(String userId);

}
