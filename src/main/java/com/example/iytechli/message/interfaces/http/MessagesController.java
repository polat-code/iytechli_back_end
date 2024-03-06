package com.example.iytechli.message.interfaces.http;

import com.example.iytechli.message.application.MessagesService;
import com.example.iytechli.message.domain.model.http.AllMessagesRequest;
import com.example.iytechli.message.domain.model.http.MessageDetailsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/messages")
public class MessagesController {
    private final MessagesService messagesService;

    @GetMapping("/all-messages")
    public ResponseEntity<List<MessageDetailsResponse>> getAllMessagesByUserId(@RequestBody AllMessagesRequest allMessagesRequest) {
       return messagesService.getAllMessagesByUserId(allMessagesRequest);
    }
}
