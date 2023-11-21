package com.example.pixpalapp.controller;

import com.example.pixpalapp.config.CustomUserDetails;
import com.example.pixpalapp.model.User;
import com.example.pixpalapp.repository.UserRepository;
import com.example.pixpalapp.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Controller
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/profile")
@RequiredArgsConstructor
public class UserController {

    UserService userService;
    UserRepository userRepository;

    @GetMapping
    public String profile(Model model, @AuthenticationPrincipal CustomUserDetails userDetails){
        Optional<User> user = userRepository.findById(userDetails.getId());
        user.ifPresent(value -> model.addAttribute("user", value));

        return "profile";
    }

    @PostMapping
    public String edit(@AuthenticationPrincipal CustomUserDetails userDetails, MultipartFile file){
        userService.updateUser(userDetails, file);
        return "redirect:/profile";
    }
}
