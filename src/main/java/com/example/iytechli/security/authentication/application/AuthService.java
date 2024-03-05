package com.example.iytechli.security.authentication.application;

import com.example.iytechli.security.authentication.domain.exceptions.AlreadyRegisteredUser;
import com.example.iytechli.security.authentication.domain.exceptions.EmailNotValidException;
import com.example.iytechli.security.authentication.domain.model.http.AuthenticationRequest;
import com.example.iytechli.security.authentication.domain.model.http.AuthenticationResponse;
import com.example.iytechli.security.authentication.domain.model.http.RegisterResponse;
import com.example.iytechli.security.authentication.domain.model.http.RegisterRequest;
import com.example.iytechli.user.domain.entity.User;
import com.example.iytechli.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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

        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);

        RegisterResponse registerResponse =  RegisterResponse.builder()
                .token(jwtToken)
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

    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getEmail(),
                            authenticationRequest.getPassword()
                    )

            );
            Optional<User> user = userRepository.findByAlreadyEmail(authenticationRequest.getEmail());
            if(user.isEmpty()) {
                throw new UsernameNotFoundException("There is no such a user");
            }

            String jwtToken = jwtService.generateToken(user.get());
            AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();

            return new ResponseEntity<>(authenticationResponse,HttpStatus.OK);

        }catch (Exception e) {
            throw new UsernameNotFoundException("There is no such a user");
        }
    }
}
