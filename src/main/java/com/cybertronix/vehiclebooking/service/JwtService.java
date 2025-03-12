package com.cybertronix.vehiclebooking.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    Boolean isTokenValid(String token, UserDetails userDetails);

    String extractUserName(String token);

    String generateToken(UserDetails userDetails);
}
