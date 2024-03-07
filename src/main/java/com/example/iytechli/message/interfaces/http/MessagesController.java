package com.example.iytechli.message.interfaces.http;

import com.example.iytechli.message.application.MessagesService;
import com.example.iytechli.message.domain.model.entity.MessageDetail;
import com.example.iytechli.message.domain.model.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/messages")
public class MessagesController {
    private final MessagesService messagesService;

    @GetMapping("/all-messages")
    public ResponseEntity<List<AllMessagesResponse>> getAllMessagesByUserId(@RequestBody AllMessagesRequest allMessagesRequest) {
       return messagesService.getAllMessagesByUserId(allMessagesRequest);
    }

    @GetMapping("/message-detail-by-id")
    public ResponseEntity<MessageDetailsResponseById> messageDetailById(@RequestBody MessageDetailsRequestById messageDetailsRequestById) throws Exception {
        return messagesService.messageDetailById(messageDetailsRequestById);
    }

    // If already a message doesn't exist then create new one.
    @GetMapping("/message-detail-by-userid")
    public ResponseEntity<MessageDetailsResponseByCrossClientUserId> messageDetailsByCrossClientUserId(@RequestBody MessageDetailsRequestByCrossClientUserId messageDetailsRequestByCrossClientUserId) {
        return messagesService.messageDetailsByCrossClientUserId(messageDetailsRequestByCrossClientUserId);
    }

    @PostMapping("/send-message")
    public ResponseEntity<String> sendMessage(@RequestBody SendMessageRequest sendMessageRequest) {
        return messagesService.sendMessage(sendMessageRequest);
    }


}

