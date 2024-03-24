package com.example.iytechli.user.interfaces.http;

import com.example.iytechli.common.domain.http.ApiResponse;
import com.example.iytechli.user.application.UserService;
import com.example.iytechli.user.domain.http.GetUserDetailResponse;
import com.example.iytechli.user.domain.http.GetUserInfoByEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
//@CrossOrigin("http://localhost:3000")
@CrossOrigin("https://iytechli-front-end-v1-jrwvnwbzh-ozgurhan-polats-projects.vercel.app")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}/{hostUserId}")
    public ResponseEntity<ApiResponse<GetUserDetailResponse>> getUserDetail(@PathVariable("userId") String userId,@PathVariable("hostUserId") String hostUserId) throws Exception{
        return userService.getUserDetail(userId,hostUserId);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<GetUserInfoByEmail>> getUserDetailByEmail(@PathVariable("email") String email) throws Exception {
        return userService.getUserDetaiLByEmail(email);
    }








}
