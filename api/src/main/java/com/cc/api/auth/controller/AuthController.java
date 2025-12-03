package com.cc.api.auth.controller;

import com.cc.api.auth.dto.request.UserLoginRequest;
import com.cc.api.auth.dto.request.UserRegisterRequest;
import com.cc.api.auth.dto.response.LoginResponseDTO;
import com.cc.api.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody UserLoginRequest user) {
        LoginResponseDTO response = userService.login(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponseDTO> register(@Valid @RequestBody UserRegisterRequest user) {
        LoginResponseDTO response = userService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}