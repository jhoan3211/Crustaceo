package com.cc.api.auth.service;

import org.springframework.beans.factory.annotation.Value;
import com.cc.api.auth.dto.request.ClaimAdminRequest;
import com.cc.api.auth.dto.request.UserLoginRequest;
import com.cc.api.auth.dto.request.UserRegisterRequest;
import com.cc.api.auth.dto.response.LoginResponseDTO;
import com.cc.api.auth.dto.response.UserResponse;
import com.cc.api.auth.entity.UserEntity;
import com.cc.api.auth.enums.Role;
import com.cc.api.auth.mapper.UserMapper;
import com.cc.api.auth.repository.UserRepository;
import com.cc.api.config.jwt.JwtProvider;
import lombok.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final UserMapper userMapper;
        private final JwtProvider jwtProvider;

        @Value("${app.admin.upgrade-code:}")
        private String upgradeCodeHash;

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

        @Transactional
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

        public UserEntity getUserByEmail(String email) {
                return userRepository.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        }


        private UserResponse buildUserResponse(UserEntity user) {
                return UserResponse.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .build();
        }


        @Transactional
        public UserResponse claimAdmin(String email, ClaimAdminRequest request) {

                if (request == null || request.getCode() == null || request.getCode().isBlank()) {
                        throw new IllegalArgumentException("Debes enviar el código secreto");
                }
                UserEntity user = getUserByEmail(email);

                if (upgradeCodeHash == null || upgradeCodeHash.isBlank()) {
                        throw new IllegalStateException("Código de activación no configurado");
                }

                boolean match = passwordEncoder.matches(request.getCode(), upgradeCodeHash);
                if (!match) {
                        throw new BadCredentialsException("Código secreto inválido");
                }

                if (user.getRole() == Role.ADMIN) {
                        user.setRole(Role.CUSTOMER);
                }else{
                        user.setRole(Role.ADMIN);
                }

                return userMapper.toResponse(userRepository.save(user));
        }

        public List<UserResponse> getAllUsers() {
                return userMapper.toResponse(userRepository.findAll());
        }
}
