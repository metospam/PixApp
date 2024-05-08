package com.example.pixpalapp.security.filter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.pixpalapp.security.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String username = extractUsername(authHeader);

        if (username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {
            authenticateUser(username);
        }

        filterChain.doFilter(request, response);
    }

    private String extractUsername(String authHeader) {
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);

            try {
                username = jwtTokenProvider.getUsername(jwt);
            } catch (TokenExpiredException e) {
                log.debug("Token has expired");
            }
        }

        return username;
    }

    private void authenticateUser(String username) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                username,
                null,
                Collections.singletonList(new SimpleGrantedAuthority("USER_ROLE"))
        );

        SecurityContextHolder.getContext().setAuthentication(token);
    }
}
