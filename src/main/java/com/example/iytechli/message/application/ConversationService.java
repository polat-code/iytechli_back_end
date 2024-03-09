package com.example.iytechli.message.application;

import com.example.iytechli.message.domain.model.entity.Conversation;
import com.example.iytechli.message.domain.model.http.ConversationRequest;
import com.example.iytechli.message.domain.model.http.ConversationsResponse;
import com.example.iytechli.message.repository.ConversationRepository;
import com.example.iytechli.user.application.UserService;
import com.example.iytechli.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final MongoTemplate mongoTemplate;
    private final UserService userService;

    public ResponseEntity<List<ConversationsResponse>> getConversationsByUserId (
            ConversationRequest conversationRequest) throws Exception
    {
        Optional<User> optionalUser = userService.findUserById(conversationRequest.getClientUserId());
        if(optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("There is no such a user " + conversationRequest.getClientUserId());
        }

        Query query = new Query();
        query.addCriteria(Criteria.where("participants").elemMatch(Criteria.where("$id").is(new ObjectId(conversationRequest.getClientUserId()))));

        List<Conversation> conversations = mongoTemplate.find(query,Conversation.class);

        List<ConversationsResponse> conversationsResponses = new ArrayList<>();

        for(Conversation conversation : conversations) {
            List<User> users = conversation.getParticipants();
            int crossClientIndex = 0;
            if(users.get(0).getId().equals(conversationRequest.getClientUserId())) {
                crossClientIndex = 1;
            }


            ConversationsResponse conversationsResponse = ConversationsResponse.builder()
                    .conversationId(conversation.getId())
                    .crossClientName(users.get(crossClientIndex).getName())
                    .crossClientSurname(users.get(crossClientIndex).getSurname())
                    .lastMessage(conversation.getLastMessage())
                    .lastMessageTime(conversation.getLastMessageTime())
                    .build();
            conversationsResponses.add(conversationsResponse);
        }

        return new ResponseEntity<>(conversationsResponses, HttpStatus.OK);
    }

    public Optional<Conversation> getConversationByClientUserIdAndCrossClientUserId(String clientUserId, String crossClientUserId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("participants")
                .all(clientUserId,crossClientUserId)
                .andOperator(Criteria.where("participants").size(2)));
        return Optional.ofNullable(mongoTemplate.findOne(query, Conversation.class));

    }

    public Conversation saveNewConversation(Conversation conversation) {
        return conversationRepository.save(conversation);
    }

    public Optional<Conversation> getConversationByConversationId(String conversationId) {
        return conversationRepository.findById(conversationId);
    }

    public void save(Conversation conversation) {
        conversationRepository.save(conversation);
    }
}
