package com.example.pixpalapp.payload.Request.User;

import com.example.pixpalapp.validator.annotaion.PasswordConfirm;
import com.example.pixpalapp.validator.annotaion.UniqueEmail;
import com.example.pixpalapp.validator.annotaion.UniqueUsername;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@UniqueUsername
@UniqueEmail
@PasswordConfirm
public class UserUpdateRequest {
    @NotBlank
    MultipartFile image;
    @NotBlank
    String username;
}
