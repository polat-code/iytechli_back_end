package com.example.iytechli.security.authentication.domain.model.http;

import lombok.*;

@AllArgsConstructor
@Builder
@Data
public class RegisterResponse {

    @NonNull
    private String token;
}
