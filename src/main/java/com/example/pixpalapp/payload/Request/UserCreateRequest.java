package com.example.pixpalapp.payload.Request;

import com.example.pixpalapp.validator.annotaion.PasswordConfirm;
import com.example.pixpalapp.validator.annotaion.UniqueEmail;
import com.example.pixpalapp.validator.annotaion.UniqueUsername;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@UniqueUsername
@UniqueEmail
@PasswordConfirm
@AllArgsConstructor
public class UserCreateRequest {

    @NotBlank
    String username;

    @NotBlank
    String email;

    @NotBlank
    String password;

    @NotBlank
    private String passwordConfirmation;
}
