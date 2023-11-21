package com.example.pixpalapp.controller;

import com.example.pixpalapp.model.dto.UserDto;
import com.example.pixpalapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthController {

     UserService userService;

    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("dto", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerForm(@ModelAttribute UserDto dto){
        userService.saveUser(dto);
        return "redirect:/login";
    }
}
