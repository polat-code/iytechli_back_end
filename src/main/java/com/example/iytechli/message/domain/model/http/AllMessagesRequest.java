package com.example.iytechli.message.domain.model.http;

import com.example.iytechli.user.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class AllMessagesRequest {

    private String userId;

}
