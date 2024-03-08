package com.example.iytechli.message.repository;

import com.example.iytechli.message.domain.model.entity.Conversation;
import com.example.iytechli.message.domain.model.entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message,String> {

    List<Message> findAllByConversation_Id(String conversationId);
}
