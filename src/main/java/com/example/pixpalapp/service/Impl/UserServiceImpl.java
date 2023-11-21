package com.example.pixpalapp.service.Impl;

import com.example.pixpalapp.config.CustomUserDetails;
import com.example.pixpalapp.model.User;
import com.example.pixpalapp.model.dto.UserDto;
import com.example.pixpalapp.repository.UserRepository;
import com.example.pixpalapp.service.StorageService;
import com.example.pixpalapp.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {

    PasswordEncoder passwordEncoder;
    UserRepository userRepository;
    StorageService storageService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(), Collections.singleton(new SimpleGrantedAuthority(user.get().getRole().getName())));
    }

    @Override
    @Transactional
    public void saveUser(UserDto userDto) {
        User user = setAttributesFromDto(new User(), userDto);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(CustomUserDetails userDetails, MultipartFile file) throws IOException {
        String filename = storageService.store(file);

        Optional<User> currentUser = userRepository.findById(userDetails.getId());
        currentUser.ifPresent(user -> user.setImagePath("/images/" + filename));
    }

    private User setAttributesFromDto(User user, UserDto dto){
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        return user;
    }

}
