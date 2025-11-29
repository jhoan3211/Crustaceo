package com.cc.api.auth.service;

import com.cc.api.auth.dto.UserResponse;
import com.cc.api.auth.dto.request.UserRequest;
import com.cc.api.auth.entity.UserEntity;
import com.cc.api.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return user;
    }

    public static ResponseEntity<UserResponse> login(UserRequest user) {
        //TODO
        return  user;
    }



}
