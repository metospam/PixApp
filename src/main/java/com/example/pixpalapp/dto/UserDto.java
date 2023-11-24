package com.example.pixpalapp.dto;


import com.example.pixpalapp.validator.PasswordConfirm;
import com.example.pixpalapp.validator.UniqueEmail;
import com.example.pixpalapp.validator.UniqueUsername;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@PasswordConfirm
@UniqueEmail
@UniqueUsername
public class UserDto {

    @NotBlank
    private String username;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    @Transient
    private String confirmPassword;
}
