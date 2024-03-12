package com.example.iytechli.security.authentication.domain.model.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePasswordRequest {

    private String newPassword;
    private String approvedRefreshPasswordCode;
    private String email;
}
