package com.cc.api.auth.service;

import com.cc.api.auth.dto.request.UserLoginRequest;
import com.cc.api.auth.dto.request.UserRegisterRequest;
import com.cc.api.auth.dto.response.LoginResponseDTO;
import com.cc.api.auth.dto.response.UserResponse;
import com.cc.api.auth.entity.UserEntity;
import com.cc.api.auth.enums.Role;
import com.cc.api.auth.repository.UserRepository;
import com.cc.api.config.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtProvider jwtProvider;

        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                return userRepository.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        }

        public LoginResponseDTO login(UserLoginRequest request) {

                UserEntity user = (UserEntity) loadUserByUsername(request.getEmail());

                if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                        throw new BadCredentialsException("Credenciales inválidas");
                }

                String token = jwtProvider.generateToken(user);

                UserResponse userResponse = buildUserResponse(user);

                return LoginResponseDTO.builder()
                        .token(token)
                        .user(userResponse)
                        .message("Login exitoso")
                        .build();
        }

        public LoginResponseDTO register(UserRegisterRequest request) {

                if (userRepository.existsByEmail(request.getEmail())) {
                        throw new IllegalStateException("El correo ya está registrado");
                }

                UserEntity user = UserEntity.builder()
                        .name(request.getName())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(Role.CUSTOMER)
                        .active(true)
                        .build();

                userRepository.save(user);

                String token = jwtProvider.generateToken(user);

                UserResponse userResponse = buildUserResponse(user);

                return LoginResponseDTO.builder()
                        .token(token)
                        .user(userResponse)
                        .message("Usuario registrado exitosamente")
                        .build();
        }


        private UserResponse buildUserResponse(UserEntity user) {
                return UserResponse.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .build();
        }
}
