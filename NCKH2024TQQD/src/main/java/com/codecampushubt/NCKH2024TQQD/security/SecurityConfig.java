package com.codecampushubt.NCKH2024TQQD.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Tắt CSRF (nếu không dùng token)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/course/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/course/add").authenticated()  // POST cần xác thực
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()) // Dùng Basic Auth
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)); // Bật session là always cho basic auth (tránh phải nhập lại tài khoản sử dụng api)

        return http.build();
    }

}