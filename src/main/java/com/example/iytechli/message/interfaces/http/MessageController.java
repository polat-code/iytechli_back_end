package com.example.iytechli.message.interfaces.http;

import com.example.iytechli.message.application.MessageService;
import com.example.iytechli.message.domain.model.http.MessageDetailRequest;
import com.example.iytechli.message.domain.model.http.MessageDetailRequestByCrossUserId;
import com.example.iytechli.message.domain.model.http.MessageDetailResponse;
import com.example.iytechli.message.domain.model.http.SaveMessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/message")
public class MessageController {

    private final MessageService messageService;
    @GetMapping("/detail/by-conversation-id")
    public ResponseEntity<List<MessageDetailResponse>> getAllMessagesByConversationId (
            @RequestBody MessageDetailRequest messageDetailRequest) throws Exception
    {
        return messageService.getAllMessagesByConversationId(messageDetailRequest);
    }

    @GetMapping("/detail/by-cross-client-id")
    public ResponseEntity<List<MessageDetailResponse>> getAllMessagesByCrossClientId(
            @RequestBody MessageDetailRequestByCrossUserId messageDetailRequestByCrossUserId) throws Exception
    {
        return messageService.getAllMessagesByCrossClientId(messageDetailRequestByCrossUserId);
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveMessage(@RequestBody SaveMessageRequest saveMessageRequest) throws Exception {
        return messageService.saveMessage(saveMessageRequest);
    }


}
