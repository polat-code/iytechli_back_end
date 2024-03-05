package com.example.iytechli.security.authentication.interfaces.http;

import com.example.iytechli.security.authentication.application.AuthService;
import com.example.iytechli.security.authentication.domain.model.http.AuthenticationRequest;
import com.example.iytechli.security.authentication.domain.model.http.AuthenticationResponse;
import com.example.iytechli.security.authentication.domain.model.http.RegisterResponse;
import com.example.iytechli.security.authentication.domain.model.http.RegisterRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private AuthService authService;

   @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) throws Exception {
        return authService.register(registerRequest);
   }

   @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
       return authService.authenticate(authenticationRequest);
   }
}
