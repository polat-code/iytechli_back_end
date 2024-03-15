package com.example.iytechli.security.authentication.domain.model.http;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {

    private String token;
    private String userId;
    private String name;
    private String surname;
    private String email;

}
