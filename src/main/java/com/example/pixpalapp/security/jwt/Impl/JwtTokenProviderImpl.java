package com.example.pixpalapp.security.jwt.Impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.pixpalapp.security.jwt.JwtTokenProvider;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.function.Function;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Configuration
public class JwtTokenProviderImpl implements JwtTokenProvider {
    @Value("${jwt.secret}")
    String secret;

    @Value("${jwt.expiration}")
    long expiration;

    public String generateToken(UserDetails userDetails) {
        return JWT.create()
                .withSubject((userDetails.getUsername()))
                .withIssuedAt(new Date())
                .withExpiresAt(new Date((new Date()).getTime() + expiration))
                .sign(Algorithm.HMAC256(secret));
    }

    public String getUsername(@NotNull String token){
        return getClaims(token).getSubject();
    }

    private DecodedJWT getClaims(@NotNull String token) throws TokenExpiredException {
        return JWT
                .require(Algorithm.HMAC256(secret))
                .build().verify(token);

    }

}
