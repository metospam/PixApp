package com.example.pixpalapp.security.jwt;

import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtTokenProvider {
    String generateToken(UserDetails userDetails);
    String getUsername(@NotNull String token);
}
