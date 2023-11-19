package com.example.pixpalapp.service;

import com.example.pixpalapp.model.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    public Long saveDto(UserDto userDto);
}
