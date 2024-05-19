package com.example.pixpalapp.service;

import com.example.pixpalapp.dto.User.UserDto;

public interface AuthService {
    void authenticate(UserDto userDto) throws Exception;
}
