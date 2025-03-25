package com.codecampushubt.NCKH2024TQQD.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Tắt CSRF (cần nếu không dùng token)
                // bảo mật api, về sau sẽ thêm bảng bảo mật với các quyền của người nhận api (front end)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/course/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/course/add").authenticated()  // POST cần xác thực
                        .anyRequest().authenticated()  // Các API khác đều cần auth
                )
                .httpBasic(Customizer.withDefaults()); // Dùng Basic Auth

        return http.build();
    }


}
