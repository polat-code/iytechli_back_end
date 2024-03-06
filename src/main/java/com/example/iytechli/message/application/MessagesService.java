package com.example.iytechli.message.application;

import com.example.iytechli.message.domain.model.entity.Messages;
import com.example.iytechli.message.repository.MessagesRepository;
import com.example.iytechli.user.domain.entity.User;
import com.example.iytechli.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MessagesService {

    private final MessagesRepository messagesRepository;
    private final UserRepository userRepository;

    public void initializeUserMessages(User user) {
        Messages messages = Messages.builder()
                .user(user)
                .messages(new ArrayList<>())
                .build();
        messagesRepository.save(messages);
    }
}
