package com.example.pixpalapp.service.Impl;

import com.example.pixpalapp.model.User;
import com.example.pixpalapp.model.dto.UserDto;
import com.example.pixpalapp.repository.UserRepository;
import com.example.pixpalapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    @Override
    @Transactional
    public Long saveDto(UserDto userDto) {
        User user = setAttributesFromDto(new User(), userDto);
        userRepository.save(user);

        return user.getId();
    }

    private User setAttributesFromDto(User user, UserDto dto){
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        return user;
    }
}
