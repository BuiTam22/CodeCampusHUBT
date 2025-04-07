package com.codecampushubt.NCKH2024TQQD.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .requestMatchers(
                        "/api/user/login",
                        "/login/show",
                        "/AdminStatic/**",
                        "/ClientStatic/**"
                ).permitAll()
                // Cho phép Spring Security xử lý phần chứng thực cơ bản
                // Phần kiểm tra permissions cụ thể sẽ được xử lý trong JwtFilter
                .anyRequest().authenticated();

        return http.build();
    }
}