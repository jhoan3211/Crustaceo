package com.cc.api.auth.controller;

import com.cc.api.auth.dto.UserResponse;
import com.cc.api.auth.dto.request.UserRequest;
import com.cc.api.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Validated RequestBody UserRequest user) {
        return  ResponseEntity.ok(userService.login(user));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest user) {
        return ResponseEntity.ok(userService.register(user));
    }
}
