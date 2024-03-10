package com.example.iytechli.compliment.interfaces.http;

import com.example.iytechli.compliment.application.ComplimentService;
import com.example.iytechli.compliment.domain.model.http.CreatePostComplimentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/compliment")
@RequiredArgsConstructor
public class ComplimentController {

    private final ComplimentService complimentService;

    @PostMapping("/post")
    public ResponseEntity<String> createPostCompliment(@RequestBody CreatePostComplimentRequest createPostComplimentRequest) throws Exception {
        return complimentService.createPostCompliment(createPostComplimentRequest);
    }
}
