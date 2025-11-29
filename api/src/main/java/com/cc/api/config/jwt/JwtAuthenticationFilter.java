package com.cc.api.config.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;


@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter implements WebFilter {
    @Override
    public Void filter(ServerWebExchange exchange, WebFilterChain chain) {
        return null;
    }
}
