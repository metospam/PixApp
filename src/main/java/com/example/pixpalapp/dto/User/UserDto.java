package com.example.pixpalapp.dto.User;

import com.example.pixpalapp.entity.Drawing;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    Long id;
    String username;
    String email;
    String password;
    String passwordConfirmation;
    MultipartFile image;
    Drawing drawing;
}
