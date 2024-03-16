package com.example.iytechli.user.domain.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetUserInfoByEmail {

    private String name;
    private String surname;
    private String userId;
}
