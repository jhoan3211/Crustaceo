package com.cc.api.auth.service;

import com.cc.api.auth.dto.response.UserResponse;
import com.cc.api.auth.dto.request.UserRequest;
import com.cc.api.auth.dto.response.LoginResponseDTO;
import com.cc.api.auth.entity.UserEntity;
import com.cc.api.auth.enums.Role;
import com.cc.api.auth.repository.UserRepository;
import com.cc.api.config.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public ResponseEntity<LoginResponseDTO> login(UserRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserEntity user = (UserEntity) authentication.getPrincipal();
        String token = jwtProvider.generateToken(user);

        UserResponse userResponse = UserResponse.builder()
                .email(user.getEmail())
                .build();

        LoginResponseDTO response = LoginResponseDTO.builder()
                .token(token)
                .user(userResponse)
                .message("Login exitoso")
                .build();

        return ResponseEntity.ok(response);
    }


    public ResponseEntity<LoginResponseDTO> register(UserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            LoginResponseDTO error = LoginResponseDTO.builder()
                    .message("usuario ya registrado")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        UserEntity user = new UserEntity();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.CUSTOMER);

        userRepository.save(user);

        String token = jwtProvider.generateToken(user);

        UserResponse userResponse = UserResponse.builder()
                .email(user.getEmail())
                .build();

        LoginResponseDTO response = LoginResponseDTO.builder()
                .token(token)
                .user(userResponse)
                .message("")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }





}
