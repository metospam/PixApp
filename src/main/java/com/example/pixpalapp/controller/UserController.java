package com.example.pixpalapp.controller;

import com.example.pixpalapp.dto.UserDto;
import com.example.pixpalapp.entity.User;
import com.example.pixpalapp.payload.Request.UserUpdateRequest;
import com.example.pixpalapp.payload.Response.UserResponse;
import com.example.pixpalapp.service.StorageService;
import com.example.pixpalapp.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.security.Principal;

@RestController
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    UserService userService;
    StorageService storageService;

    @PutMapping
    public ResponseEntity<String> handleUpdateUser(UserUpdateRequest userRequest, Principal principal) throws IOException {
        User user = userService.getUserFromPrincipal(principal);

        UserDto userDto = UserDto.builder()
                .image(userRequest.getImage())
                .build();

        userService.updateUser(user, userDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/current")
    public ResponseEntity<UserResponse> handleGetAuthUser(Principal principal) throws AccessDeniedException {
        User user = userService.getUserFromPrincipal(principal);
        String imageBase64 = storageService.encodeImage(user.getImage());

        return ResponseEntity.ok(new UserResponse(imageBase64, user));
    }
}
