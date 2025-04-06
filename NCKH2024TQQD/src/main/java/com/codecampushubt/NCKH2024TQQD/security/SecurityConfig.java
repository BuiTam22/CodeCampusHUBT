package com.codecampushubt.NCKH2024TQQD.security;

import com.codecampushubt.NCKH2024TQQD.security.JwtFilter;
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
                .csrf(csrf -> csrf.disable()) // Thêm dòng này để tắt CSRF
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .requestMatchers(
                        "/api/user/login",
                        "/login/show",
                        "/AdminStatic/**", // Cho phép tất cả tài nguyên static AdminStatic
                        "/ClientStatic/**" // Cho phép tất cả tài nguyên static ClientStatic
                ).permitAll()
                .requestMatchers("/admin/**").authenticated() // Chặn truy cập các controller trong /admin/** nếu chưa login
                .anyRequest().authenticated();

        return http.build();
    }
}