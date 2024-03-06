package com.example.iytechli.message.repository;

import com.example.iytechli.message.domain.model.entity.MessageDetail;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageDetailRepository extends MongoRepository<MessageDetail,String> {
}
