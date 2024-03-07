package com.example.iytechli.message.domain.model.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MessageDetailsRequestById {
    private String messageDetailId;
}
