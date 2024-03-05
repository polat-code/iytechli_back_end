package com.example.iytechli.security.authentication.interfaces.http;

import com.example.iytechli.security.authentication.application.AuthService;
import com.example.iytechli.security.authentication.domain.model.http.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

   @GetMapping("/verify-otp")
    public ResponseEntity<OtpVerificationResponse> verifyOTP(@RequestBody OtpVerificationRequest otpVerificationRequest) throws Exception{
       return authService.verifyOTP(otpVerificationRequest);
   }
}
