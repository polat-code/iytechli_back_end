package com.example.iytechli.message.application;

import com.example.iytechli.message.domain.exceptions.MessageDetailNotFoundException;
import com.example.iytechli.message.domain.model.entity.MessageDetail;
import com.example.iytechli.message.domain.model.entity.Messages;
import com.example.iytechli.message.domain.model.entity.UserType;
import com.example.iytechli.message.domain.model.http.*;
import com.example.iytechli.message.repository.MessageDetailRepository;
import com.example.iytechli.message.repository.MessagesRepository;
import com.example.iytechli.user.domain.entity.User;
import com.example.iytechli.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessagesService {

    private final MessagesRepository messagesRepository;
    private final UserRepository userRepository;
    private final MessageDetailRepository messageDetailRepository;

    public void initializeUserMessages(User user) {
        Messages messages = Messages.builder()
                .user(user)
                .messages(new ArrayList<>())
                .build();
        messagesRepository.save(messages);
    }

    public ResponseEntity<List<AllMessagesResponse>> getAllMessagesByUserId(AllMessagesRequest allMessagesRequest) {
        Messages messages = messagesRepository.findByUser_Id(allMessagesRequest.getUserId());

        List<AllMessagesResponse> allMessagesRespons = new ArrayList<>();
        for(MessageDetail messageDetail : messages.getMessages()) {
            int messageDetailsSize = messageDetail.getMessages().size();
            String lastMessage = messageDetail.getMessages().get(messageDetailsSize - 1).getMessage();
            UserType lastMessageBy = messageDetail.getMessages().get(messageDetailsSize - 1).getUserType();
            AllMessagesResponse allMessagesResponse = AllMessagesResponse.builder()
                    .messageDetailId(messageDetail.getId())
                    .crossClientUserName(messageDetail.getCrossClientUser().getName())
                    .crossClientUserSurname(messageDetail.getCrossClientUser().getSurname())
                    .createdAt(messageDetail.getCreatedAt())
                    .lastMessage(lastMessage)
                    .lastMessageBy(lastMessageBy)
                    .build();
            allMessagesRespons.add(allMessagesResponse);
        }

        return new ResponseEntity<>(allMessagesRespons, HttpStatus.OK);


    }

    public ResponseEntity<MessageDetailsResponseById> messageDetailById(MessageDetailsRequestById messageDetailsRequestById) throws Exception {
        Optional<MessageDetail> optionalMessageDetail = messageDetailRepository.findById(messageDetailsRequestById.getMessageDetailId());
        if(optionalMessageDetail.isEmpty()) {
            throw new MessageDetailNotFoundException("Wrong messageDetail id");
        }
        MessageDetail messageDetail = optionalMessageDetail.get();
        MessageDetailsResponseById messageDetailsResponseById = MessageDetailsResponseById.builder()
                .messageDetailId(messageDetail.getId())
                .messages(messageDetail.getMessages())
                .crossClientName(messageDetail.getCrossClientUser().getName())
                .crossClientSurname(messageDetail.getCrossClientUser().getSurname())
                .build();
        return new ResponseEntity<>(messageDetailsResponseById,HttpStatus.OK) ;

    }

    public ResponseEntity<MessageDetailsResponseByCrossClientUserId> messageDetailsByCrossClientUserId(
            MessageDetailsRequestByCrossClientUserId messageDetailsRequestByCrossClientUserId) {
        Messages userMessages = messagesRepository.findByUser_Id(messageDetailsRequestByCrossClientUserId.getClientUserId());
        List<MessageDetail> messages = userMessages.getMessages();
        MessageDetail foundMessageDetail = null;
        for(MessageDetail messageDetail : messages) {
            if(messageDetail.getCrossClientUser().getId().equals(messageDetailsRequestByCrossClientUserId.getCrossClientUserId())) {
                foundMessageDetail = messageDetail;
                break;
            }
        }

        if(foundMessageDetail == null) {
            // create a new chat
            Optional<User> crossClientUser = userRepository.findById(messageDetailsRequestByCrossClientUserId.getCrossClientUserId());
            if(crossClientUser.isEmpty()){
                throw new UsernameNotFoundException("Cross client id is wrong");
            }
            Optional<User> clientUser = userRepository.findById(messageDetailsRequestByCrossClientUserId.getClientUserId());
            if(clientUser.isEmpty()) {
                throw new UsernameNotFoundException("Client id is wrong");
            }

            MessageDetail messageDetail = MessageDetail.builder()
                    .crossClientUser(crossClientUser.get())
                    .clientUser(clientUser.get())
                    .createdAt(new Date())
                    .messages(new ArrayList<>())
                    .build();
            userMessages.getMessages().add(messageDetail);
            userMessages = messagesRepository.save(userMessages);

            // Get the index of the last element
            int userMessagesSize = userMessages.getMessages().size();
            foundMessageDetail = userMessages.getMessages().get(userMessagesSize - 1);

        }


        // return all necessary MessageDetail info
        MessageDetailsResponseByCrossClientUserId messageDetailsResponseByCrossClientUserId =
                MessageDetailsResponseByCrossClientUserId.builder()
                        .messageDetailId(foundMessageDetail.getId())
                        .crossClientName(foundMessageDetail.getCrossClientUser().getName())
                        .crossClientSurname(foundMessageDetail.getCrossClientUser().getSurname())
                        .messages(new ArrayList<>())
                        .build();

        return new ResponseEntity<>(messageDetailsResponseByCrossClientUserId,HttpStatus.OK);

    }

    public ResponseEntity<String> sendMessage(SendMessageRequest sendMessageRequest) {
    }
}
