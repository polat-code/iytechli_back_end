package com.example.iytechli.security.authentication.application;

import com.example.iytechli.common.domain.http.ApiResponse;
import com.example.iytechli.security.authentication.domain.exceptions.*;
import com.example.iytechli.security.authentication.domain.model.http.*;
import com.example.iytechli.user.domain.entity.User;
import com.example.iytechli.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AuthEmailService  authEmailService;


    @Value("${application.otp.otpExpiration}")
    private  long otpExpirationMilliSeconds;

    public ResponseEntity<ApiResponse<RegisterResponse>> register(RegisterRequest registerRequest) throws Exception{
        //  Check email is iyte email or not.
        String email = registerRequest.getEmail();
        checkEmail(email);


        Optional<User> optionalUser = userRepository.findByAlreadyEmail(registerRequest.getEmail());
        if(optionalUser.isPresent()) {
            throw new AlreadyRegisteredUser("Already Registered Email!");
        }


        User user = User.builder()
                .name(registerRequest.getName())
                .surname(registerRequest.getSurname())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .createdAt(new Date())
                .updatedAt(new Date())
                .isAbleToSentPost(true)
                .phoneNumber(registerRequest.getPhoneNumber())
                .build();
        // send otp
        sendOTP(user);

        // Send jwt token in otp verification.
        // String jwtToken = jwtService.generateToken(user);

        RegisterResponse registerResponse =  RegisterResponse.builder()
                .message("verification code is sent to your email address")
                .build();
        log.info("Email is sent to " + registerRequest.getEmail());

        return new ResponseEntity<>(new ApiResponse<>(registerResponse,"successfully registered",200,true,new Date()), HttpStatus.OK);
    }

    private void checkEmail(String email) throws Exception {
        String[] splitString = email.split("@");
        if(splitString.length <= 1) {
            throw new EmailNotValidException("Invalid Email");
        }
        if(!(splitString[1].equals("std.iyte.edu.tr") || splitString[1].equals("iyte.edu.tr"))){
            throw new EmailNotValidException("Invalid Email");
        }


    }

    private void sendOTP(User user) throws Exception {

        String newoOtp = RandomStringUtils.randomNumeric(6);
        user.setOtp(newoOtp);

        Date otpExpiredDate = new Date(System.currentTimeMillis() + otpExpirationMilliSeconds);
        user.setOtpExpiredDate(otpExpiredDate);

        userRepository.save(user);

        // send an email
        authEmailService.sendAuthEmail(user.getOtp(), user.getName(), user.getSurname(), user.getEmail());

    }

    public ResponseEntity<ApiResponse<AuthenticationResponse>> authenticate(AuthenticationRequest authenticationRequest) throws Exception{

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getEmail(),
                            authenticationRequest.getPassword()
                    )

            );

        Optional<User> optionalUser = checkUser(authenticationRequest.getEmail());

        User user = optionalUser.get();

            // Check that user approve its email or not .
            if(!(user.getOtp() == null)){
                sendOTP(user);
                throw new OtpNotApprovedException("OTP is not approved. New OTP is sent to email");
            }

            String jwtToken = jwtService.generateToken(user);
            AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();

            return new ResponseEntity<>(new ApiResponse<>(authenticationResponse,"Authenticate successful",200,true,new Date()) ,HttpStatus.OK);
    }

    private Optional<User> checkUser(String email) throws Exception {
        Optional<User> optionalUser = userRepository.findByAlreadyEmail(email);
        if(optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("There is no such a user");
        }
        return optionalUser;
    }


    public ResponseEntity<ApiResponse<OtpVerificationResponse>> verifyOTP(OtpVerificationRequest otpVerificationRequest) throws Exception {
        // Check whether email is valid
        checkEmail(otpVerificationRequest.getEmail());

        // Check authentication
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    otpVerificationRequest.getEmail(),
                    otpVerificationRequest.getPassword()
            ));

            Optional<User> optionalUser = userRepository.findByAlreadyEmail(otpVerificationRequest.getEmail());
            if(optionalUser.isEmpty()) {
                throw new UsernameNotFoundException("There is no such a email");
            }

            User user = optionalUser.get();

            // Verify OTP
            String requestOTP = otpVerificationRequest.getOtp();
            if(!requestOTP.equals(user.getOtp())) {
                throw new OtpNotValidException("OTP is invalid");
            }
            if(user.getOtpExpiredDate().before(new Date(System.currentTimeMillis()))) {
                sendOTP(user);
                throw new OtpExpirationException();
            }

            user.setOtp(null);
            user.setOtpExpiredDate(null);

            var jwtToken = jwtService.generateToken(user);

            user = userRepository.save(user);
            // TODO Don't send jwt token to user. Direct the user into login page.

            OtpVerificationResponse otpVerificationResponse = OtpVerificationResponse.builder()
                    .token(jwtToken)
                    .build();

            return new ResponseEntity<>(new ApiResponse<>(otpVerificationResponse,"OTP Verification successful",200,true,new Date()) ,HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<String>> forgetPassword(ForgetPasswordRequest forgetPasswordRequest) throws Exception{
        Optional<User> optionalUser = checkUser(forgetPasswordRequest.getEmail());

        if(!(optionalUser.get().getOtp() == null)) {
            throw new OtpNotApprovedException("Otp is not approved");
        }

        sendRefreshPasswordCode(optionalUser.get());

        return new ResponseEntity<>(new ApiResponse<>("Code is sent to email","",200,true,new Date()),HttpStatus.OK);
    }

    private void sendRefreshPasswordCode(User user) throws Exception {
        String refreshPasswordCode = RandomStringUtils.randomNumeric(6);
        user.setRefreshPasswordCode(refreshPasswordCode);

        userRepository.save(user);
        // send an email
        authEmailService.sendRefreshPasswordCodeEmail(user.getRefreshPasswordCode(), user.getName(), user.getSurname(), user.getEmail());
    }

    public ResponseEntity<ApiResponse<VerifyForgetPasswordResponse>> verifyForgetPasswordCode(
            VerifyForgetPasswordRequest verifyForgetPasswordRequest) throws Exception
    {
        Optional<User> optionalUser = checkUser(verifyForgetPasswordRequest.getEmail());
        if(optionalUser.get().getRefreshPasswordCode() == null) {
            throw new RefreshPasswordCodeInvalidException("Error ! Please leave this page");
        }

        if(!optionalUser.get().getRefreshPasswordCode().equals(verifyForgetPasswordRequest.getRefreshPasswordCode())) {
            throw new RefreshPasswordCodeInvalidException("Invalid Password Code");
        }
        User user = optionalUser.get();
        user.setRefreshPasswordCode(null);

        // This is authentication when user enters new Password
        String approvedRefreshPasswordCode = RandomStringUtils.randomNumeric(10);
        user.setApprovedRefreshPasswordCode(approvedRefreshPasswordCode);

        userRepository.save(user);

        VerifyForgetPasswordResponse verifyForgetPasswordResponse =  VerifyForgetPasswordResponse.builder()
                .approvedRefreshPasswordCode(approvedRefreshPasswordCode)
                .build();

        return new ResponseEntity<>(new ApiResponse<>(
                verifyForgetPasswordResponse,"Successfully approved",200,true,new Date()),
                HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<String>> changePassword(
            ChangePasswordRequest changePasswordRequest) throws Exception
    {
        Optional<User> optionalUser = checkUser(changePasswordRequest.getEmail());
        User user = optionalUser.get();
        if(user.getApprovedRefreshPasswordCode() == null) {
            throw new ApprovedRefreshPasswordCodeException("Invalid page. please leave this page");
        }
        if(!user.getApprovedRefreshPasswordCode().equals(changePasswordRequest.getApprovedRefreshPasswordCode())) {
            throw new ApprovedRefreshPasswordCodeException("Invalid approved refresh token");
        }

        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        user.setApprovedRefreshPasswordCode(null);
        userRepository.save(user);

        return new ResponseEntity<>(
                new ApiResponse<>("Changing password is successful","",200,true,new Date()),
                HttpStatus.OK);
    }
}
