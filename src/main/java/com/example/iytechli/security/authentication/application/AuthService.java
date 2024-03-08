package com.example.iytechli.security.authentication.application;

import com.example.iytechli.security.authentication.domain.exceptions.*;
import com.example.iytechli.security.authentication.domain.model.http.*;
import com.example.iytechli.user.domain.entity.User;
import com.example.iytechli.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AuthEmailService  authEmailService;


    @Value("${application.otp.otpExpiration}")
    private  long otpExpirationMilliSeconds;

    public ResponseEntity<RegisterResponse> register(RegisterRequest registerRequest) throws Exception{
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

        return new ResponseEntity<>(registerResponse, HttpStatus.OK);
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

    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest authenticationRequest) throws Exception{

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getEmail(),
                            authenticationRequest.getPassword()
                    )

            );

            Optional<User> optionalUser = userRepository.findByAlreadyEmail(authenticationRequest.getEmail());
            if(optionalUser.isEmpty()) {
                throw new UsernameNotFoundException("There is no such a user");
            }

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

            return new ResponseEntity<>(authenticationResponse,HttpStatus.OK);
    }


    public ResponseEntity<OtpVerificationResponse> verifyOTP(OtpVerificationRequest otpVerificationRequest) throws Exception {
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
                throw new OtpExpirationException();
            }

            user.setOtp(null);
            user.setOtpExpiredDate(null);

            var jwtToken = jwtService.generateToken(user);

            user = userRepository.save(user);


            OtpVerificationResponse otpVerificationResponse = OtpVerificationResponse.builder()
                    .token(jwtToken)
                    .build();

            return new ResponseEntity<>(otpVerificationResponse,HttpStatus.OK);
    }
}
