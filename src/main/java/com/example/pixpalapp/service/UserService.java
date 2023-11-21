package com.example.pixpalapp.service;

import com.example.pixpalapp.config.CustomUserDetails;
import com.example.pixpalapp.model.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

public interface UserService extends UserDetailsService {
    void saveUser(UserDto userDto);
    void updateUser(CustomUserDetails userDetails, MultipartFile file);
}
