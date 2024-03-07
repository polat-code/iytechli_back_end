package com.example.iytechli.message.domain.model.http;

import com.example.iytechli.message.domain.model.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class MessageDetailsResponseById {

    private String messageDetailId;
    private String crossClientName;
    private String crossClientSurname;
    private List<Message> messages;

}
