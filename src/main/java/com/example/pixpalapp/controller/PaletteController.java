package com.example.pixpalapp.controller;

import com.example.pixpalapp.dto.PaletteDto;
import com.example.pixpalapp.entity.Color;
import com.example.pixpalapp.entity.Tag;
import com.example.pixpalapp.entity.User;
import com.example.pixpalapp.payload.Request.PaletteCreateRequest;
import com.example.pixpalapp.service.ColorService;
import com.example.pixpalapp.service.PaletteService;
import com.example.pixpalapp.service.TagService;
import com.example.pixpalapp.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.List;

@RequestMapping("api/v1/palettes")
@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class PaletteController {

    ColorService colorService;
    UserService userService;
    PaletteService paletteService;
    TagService tagService;

    @PostMapping
    public ResponseEntity<String> handleCreatePalette(@RequestBody @Valid PaletteCreateRequest request,
                                                      @NotNull Principal principal) throws AccessDeniedException {
        User user = userService.getUserFromPrincipal(principal);

        PaletteDto paletteDto = PaletteDto.builder()
                .name(request.getName())
                .colorsCodes(request.getColorsCodes())
                .tagsNames(request.getTagsNames())
                .build();

        List<Color> colors = colorService.createColors(paletteDto.getColorsCodes());
        List<Tag> tags = tagService.createTags(paletteDto.getTagsNames());

        paletteService.createPalette(paletteDto.getName(), user, colors, tags);

        return ResponseEntity.ok().build();
    }
}