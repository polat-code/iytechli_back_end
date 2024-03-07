package com.example.iytechli.message.domain.model.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MessageDetailsRequestByCrossClientUserId {

    private String clientUserId;
    private String crossClientUserId;

}
