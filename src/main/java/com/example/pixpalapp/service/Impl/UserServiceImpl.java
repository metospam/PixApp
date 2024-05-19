package com.example.pixpalapp.service.Impl;

import com.example.pixpalapp.dto.Drawing.DrawingDto;
import com.example.pixpalapp.dto.Palette.PaletteDto;
import com.example.pixpalapp.dto.User.UserDto;
import com.example.pixpalapp.entity.Drawing;
import com.example.pixpalapp.entity.Palette;
import com.example.pixpalapp.entity.User;
import com.example.pixpalapp.repository.DrawingRepository;
import com.example.pixpalapp.repository.PaletteRepository;
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
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {

    PasswordEncoder passwordEncoder;
    UserRepository userRepository;
    DrawingRepository drawingRepository;
    PaletteRepository paletteRepository;

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
    public void updateUser(User user, UserDto userDto) throws IOException {
        user.setUsername(userDto.getUsername());
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

    public void toggleFavoriteDrawing(User user, DrawingDto drawingDto) {
        Drawing drawing = drawingRepository.findById(drawingDto.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Drawing not found"));

        List<Drawing> favoriteDrawings = user.getFavoriteDrawings();
        favoriteDrawings.add(drawing);
        user.setFavoriteDrawings(favoriteDrawings);

        userRepository.save(user);
    }

    public void toggleFavoritePalette(User user, PaletteDto paletteDto) {
        Palette palette = paletteRepository.findById(paletteDto.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Palette not found"));

        List<Palette> favoritePalettes = user.getFavoritePalettes();

        if (favoritePalettes.contains(palette)) {
            favoritePalettes.remove(palette);
        } else {
            favoritePalettes.add(palette);
        }

        user.setFavoritePalettes(favoritePalettes);

        userRepository.save(user);
    }

    private User setAttributesFromRequest(User user, UserDto userDto) {
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return user;
    }

}
