package com.example.pixpalapp.service;

import com.example.pixpalapp.dto.Drawing.DrawingDto;
import com.example.pixpalapp.dto.Palette.PaletteDto;
import com.example.pixpalapp.dto.User.UserDto;
import com.example.pixpalapp.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.security.Principal;

public interface UserService extends UserDetailsService {
    void saveUser(UserDto userDto) throws IOException;
    void updateUser(User user, UserDto userDto) throws IOException;
    User getUserFromPrincipal(Principal principal) throws AccessDeniedException;
    void toggleFavoriteDrawing(User user, DrawingDto drawingDto);
    void toggleFavoritePalette(User user, PaletteDto paletteDto);
}
