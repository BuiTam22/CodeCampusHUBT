package com.codecampushubt.NCKH2024TQQD.service.JWTServices;

import io.jsonwebtoken.Claims;

import java.util.List;

public interface JwtService {
     String generateToken(String username, List<String> permissions);
     Claims extractClaims(String token);
     String extractUsername(String token);
     boolean validateToken(String token);
     List<String> extractPermissions(String token);
}
