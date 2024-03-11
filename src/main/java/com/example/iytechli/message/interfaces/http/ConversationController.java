package com.example.iytechli.message.interfaces.http;

import com.example.iytechli.common.domain.http.ApiResponse;
import com.example.iytechli.message.application.ConversationService;
import com.example.iytechli.message.domain.model.http.ConversationRequest;
import com.example.iytechli.message.domain.model.http.ConversationsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/conversation")
public class ConversationController {

    private final ConversationService conversationService;
    
    @GetMapping("/all/by-userid")
    public ResponseEntity<ApiResponse<List<ConversationsResponse>>> getConversationsByUserId(@RequestBody ConversationRequest conversationRequest) throws Exception{
        return conversationService.getConversationsByUserId(conversationRequest);
    }


}
