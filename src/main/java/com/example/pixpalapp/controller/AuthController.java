package com.example.pixpalapp.controller;

import com.example.pixpalapp.dto.UserDto;
import com.example.pixpalapp.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthController {

     UserService userService;

    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("dto", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerForm(@Valid @ModelAttribute("dto") UserDto dto,
                               BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("dto", dto);
            return "register";
        }

        userService.saveUser(dto);
        return "redirect:/login";
    }
}
