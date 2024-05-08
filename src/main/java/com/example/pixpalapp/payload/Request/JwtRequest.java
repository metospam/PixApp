package com.example.pixpalapp.payload.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
public class JwtRequest {

    @NotBlank
    String username;

    @NotBlank
    String password;

}
