package com.example.iytechli.message.application;

import com.example.iytechli.message.domain.exceptions.ConversationNotFoundException;
import com.example.iytechli.message.domain.exceptions.MessageDetailNotFoundExcepiton;
import com.example.iytechli.message.domain.model.entity.Conversation;
import com.example.iytechli.message.domain.model.entity.Message;
import com.example.iytechli.message.domain.model.entity.UserMessageType;
import com.example.iytechli.message.domain.model.http.MessageDetailRequest;
import com.example.iytechli.message.domain.model.http.MessageDetailRequestByCrossUserId;
import com.example.iytechli.message.domain.model.http.MessageDetailResponse;
import com.example.iytechli.message.domain.model.http.SaveMessageRequest;
import com.example.iytechli.message.repository.MessageRepository;
import com.example.iytechli.user.application.UserService;
import com.example.iytechli.user.domain.entity.User;
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
public class MessageService {

    private final MessageRepository messageRepository;
    private final ConversationService conversationService;
    private final UserService userService;

    public ResponseEntity<List<MessageDetailResponse>> getAllMessagesByConversationId(
            MessageDetailRequest messageDetailRequest) throws Exception
    {
        List<Message> messages = messageRepository.findAllByConversation_Id(messageDetailRequest.getConversationId());
        if(messages.isEmpty()) {
            // TODO Add a handler for this exception
            throw new MessageDetailNotFoundExcepiton("Conversation id is not found");
        }
        List<MessageDetailResponse> messageDetailResponseList = convertAllMessagesConversationHas(messages,messageDetailRequest.getClientUserId());

        return new ResponseEntity<>(messageDetailResponseList, HttpStatus.OK);

    }

    public ResponseEntity<List<MessageDetailResponse>> getAllMessagesByCrossClientId(
            MessageDetailRequestByCrossUserId messageDetailRequestByCrossUserId) throws Exception
    {
        Optional<Conversation> optionalConversation = conversationService
                .getConversationByClientUserIdAndCrossClientUserId(
                        messageDetailRequestByCrossUserId.getClientUserId(),
                        messageDetailRequestByCrossUserId.getCrossClientUserId());

        Conversation foundConversation = null;

        if(optionalConversation.isEmpty()) {

            List<User> participants = new ArrayList<>();

            // Check whether there are users.
            Optional<User> clientUserOptional = userService.findUserById(messageDetailRequestByCrossUserId.getClientUserId());
            if(clientUserOptional.isEmpty()) {
                throw new UsernameNotFoundException("There is no such a client user "
                        + messageDetailRequestByCrossUserId.getClientUserId() );
            }

            Optional<User> crossClientUserOptional = userService.findUserById(messageDetailRequestByCrossUserId.getCrossClientUserId());
            if(crossClientUserOptional.isEmpty()) {
                throw new UsernameNotFoundException("There is no such a cross client user"
                        + messageDetailRequestByCrossUserId.getCrossClientUserId());
            }

            // add participants
            participants.add(clientUserOptional.get());
            participants.add(crossClientUserOptional.get());

            // Create new Conversation
            Conversation conversation = Conversation.builder()
                    .participants(participants)
                    .build();

            foundConversation = conversationService.saveNewConversation(conversation);

            // There is no any message.
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.OK);
        }else {
            foundConversation = optionalConversation.get();
        }

        // Find all messages that conversation has
        List<Message> messages =  messageRepository.findAllByConversation_Id(foundConversation.getId());

        List<MessageDetailResponse> messageDetailResponseList = convertAllMessagesConversationHas(messages,messageDetailRequestByCrossUserId.getClientUserId());
        return new ResponseEntity<>(messageDetailResponseList,HttpStatus.OK);

    }

    private  List<MessageDetailResponse> convertAllMessagesConversationHas(List<Message> messages, String clientUserId) {
        List<MessageDetailResponse> messageDetailResponseList = new ArrayList<>();
        for(Message message : messages) {
            MessageDetailResponse messageDetailResponse =
                    MessageDetailResponse.builder()
                            .messageOwner(message.getSenderUser().equals(clientUserId)
                                    ? UserMessageType.CLIENT
                                    : UserMessageType.CROSS_CLIENT)
                            .content(message.getContent())
                            .sentAt(message.getCreatedAt())
                            .build();

            messageDetailResponseList.add(messageDetailResponse);
        }
        return messageDetailResponseList;

    }


    public ResponseEntity<String> saveMessage(SaveMessageRequest saveMessageRequest) throws Exception {
        Optional<Conversation> conversation = conversationService.getConversationByConversationId(saveMessageRequest.getConversationId());
        if(conversation.isEmpty()) {
            throw new ConversationNotFoundException("There is no such a conversation " + saveMessageRequest.getConversationId());
        }

        Optional<User> optionalUser = userService.findUserById(saveMessageRequest.getClientId());
        if(optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("There is no such a client id"
                    + saveMessageRequest.getClientId());
        }

        Message message = Message.builder()
                .createdAt(new Date())
                .senderUser(optionalUser.get())
                .conversation(conversation.get())
                .content(saveMessageRequest.getContent())
                .build();
        message = messageRepository.save(message);

        // Change last message info of conversation
        conversation.get().setLastMessage(saveMessageRequest.getContent());
        conversation.get().setLastMessageTime(new Date());

        conversationService.save(conversation.get());

        return new ResponseEntity<>("Message is added successfully",HttpStatus.OK);
    }
}
