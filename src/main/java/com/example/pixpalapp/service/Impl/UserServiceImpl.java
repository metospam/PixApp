package com.example.pixpalapp.service.Impl;

import com.example.pixpalapp.dto.UserDto;
import com.example.pixpalapp.entity.User;
import com.example.pixpalapp.repository.UserRepository;
import com.example.pixpalapp.security.details.CustomUserDetails;
import com.example.pixpalapp.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {

    PasswordEncoder passwordEncoder;
    UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = setAttributesFromRequest(new User(), userDto);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(User user, UserDto userDto) throws IOException {
        user.setImage(userDto.getImage().getBytes());
        userRepository.save(user);
    }

    @Override
    public User getUserFromPrincipal(Principal principal) {
        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        String username = principal.getName();
        Optional<User> optionalUser = userRepository.findByUsername(username);

        return optionalUser.orElseThrow(() ->
                new UsernameNotFoundException("User not found with username: " + username));
    }

    private User setAttributesFromRequest(User user, UserDto userDto) {
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return user;
    }

}
