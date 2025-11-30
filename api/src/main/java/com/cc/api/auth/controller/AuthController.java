package com.cc.api.auth.controller;

import com.cc.api.auth.dto.request.UserRequest;
import com.cc.api.auth.dto.response.LoginResponseDTO;
import com.cc.api.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody UserRequest user) {
        return userService.login(user);
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponseDTO> register(@RequestBody UserRequest user) {
        return userService.register(user);
    }
}
