package com.gearfound.authservice.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class TestSecurityConfiguration {
    @Bean
    public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.POST, "/user").permitAll()
                .pathMatchers(HttpMethod.GET, "/user").authenticated()
                .pathMatchers(HttpMethod.DELETE, "/oauth/refresh-token/1234").permitAll()
                .anyExchange().authenticated()
                .and().build();
    }
}
