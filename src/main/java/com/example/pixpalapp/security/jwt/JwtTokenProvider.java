package com.example.pixpalapp.security.jwt;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

public interface JwtTokenProvider {
    String generateToken(UserDetails userDetails);
    String getUsername(@NotNull String token);
}
