package com.example.pixpalapp.controller;

import com.example.pixpalapp.dto.User.UserDto;
import com.example.pixpalapp.manager.ErrorManager;
import com.example.pixpalapp.payload.Request.JwtRequest;
import com.example.pixpalapp.payload.Request.User.UserCreateRequest;
import com.example.pixpalapp.payload.Response.JwtResponse;
import com.example.pixpalapp.security.details.CustomUserDetails;
import com.example.pixpalapp.security.jwt.JwtTokenProvider;
import com.example.pixpalapp.service.AuthService;
import com.example.pixpalapp.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthController {

    UserService userService;
    JwtTokenProvider jwtTokenProvider;
    AuthService authService;
    ErrorManager errorManager;

    @PostMapping("/login")
    public ResponseEntity<?> handleAuthUser(@RequestBody @Valid JwtRequest jwtRequest,
                                            BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldErrors().get(0);

            return ResponseEntity.unprocessableEntity().body(errorManager.displayError(fieldError));
        }

        UserDto userDto = UserDto.builder()
                .username(jwtRequest.getUsername())
                .password(jwtRequest.getPassword())
                .build();

        try {
            CustomUserDetails userDetails = (CustomUserDetails) userService.loadUserByUsername(userDto.getUsername());
            String token = jwtTokenProvider.generateToken(userDetails);

            authService.authenticate(userDto);

            return ResponseEntity.ok(new JwtResponse(token));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> handleRegisterUser(@RequestBody @Valid UserCreateRequest userRequest,
                                                BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            
            FieldError fieldError = bindingResult.getFieldErrors().get(0);

            return ResponseEntity.unprocessableEntity().body(errorManager.displayError(fieldError));
        }

        UserDto userDto = UserDto.builder()
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .build();

        userService.saveUser(userDto);

        return ResponseEntity.ok("User created.");
    }
}
