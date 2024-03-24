package com.example.iytechli.security.authentication.interfaces.http;

import com.example.iytechli.common.domain.http.ApiResponse;
import com.example.iytechli.security.authentication.application.AuthService;
import com.example.iytechli.security.authentication.domain.model.http.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
//@CrossOrigin("http://localhost:3000")
@CrossOrigin("https://iytechli-front-end-v1-jrwvnwbzh-ozgurhan-polats-projects.vercel.app")
public class AuthController {

    private AuthService authService;

   @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@RequestBody RegisterRequest registerRequest) throws Exception {
        return authService.register(registerRequest);
   }

   @PostMapping("/authenticate")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
       return authService.authenticate(authenticationRequest);
   }

   @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<OtpVerificationResponse>> verifyOTP(@RequestBody OtpVerificationRequest otpVerificationRequest) throws Exception{
       return authService.verifyOTP(otpVerificationRequest);
   }

   @PostMapping("/forget-password")
    public ResponseEntity<ApiResponse<String>> forgetPassword(@RequestBody ForgetPasswordRequest forgetPasswordRequest) throws Exception {
       return authService.forgetPassword(forgetPasswordRequest);
   }

   @PostMapping("/verify-forget-password-code")
    public ResponseEntity<ApiResponse<VerifyForgetPasswordResponse>> verifyForgetPasswordCode(
            @RequestBody VerifyForgetPasswordRequest verifyForgetPasswordRequest) throws Exception {
       return authService.verifyForgetPasswordCode(verifyForgetPasswordRequest);
   }

   @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<String>> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) throws Exception {
       return authService.changePassword(changePasswordRequest);
   }


}
