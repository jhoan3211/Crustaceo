package com.cc.api.auth.controller;

import com.cc.api.auth.dto.request.ClaimAdminRequest;
import com.cc.api.auth.dto.request.UserLoginRequest;
import com.cc.api.auth.dto.request.UserRegisterRequest;
import com.cc.api.auth.dto.response.LoginResponseDTO;
import com.cc.api.auth.dto.response.UserResponse;
import com.cc.api.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> response = userService.getAllUsers();
        return ResponseEntity.ok(response);
    }


    @PostMapping("/claim-admin")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    public ResponseEntity<UserResponse> claimAdmin(@RequestBody ClaimAdminRequest request) {
        String email = getMyEmail();
        UserResponse response = userService.claimAdmin(email, request);
        return ResponseEntity.ok(response);
    }



    private String getMyEmail() {

        var auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        return auth.getName();

    }

}