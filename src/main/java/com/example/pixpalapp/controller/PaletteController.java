package com.example.pixpalapp.controller;

import com.example.pixpalapp.dto.Palette.GetPalettesDto;
import com.example.pixpalapp.dto.Palette.PaletteDto;
import com.example.pixpalapp.entity.*;
import com.example.pixpalapp.payload.Request.Palette.GetPalettesRequest;
import com.example.pixpalapp.payload.Request.Palette.PaletteCreateRequest;
import com.example.pixpalapp.payload.Request.Palette.PaletteUpdateRequest;
import com.example.pixpalapp.payload.Response.Palette.GetPaletteResponse;
import com.example.pixpalapp.payload.Response.Palette.GetPalettesResponse;
import com.example.pixpalapp.repository.PaletteRepository;
import com.example.pixpalapp.service.ColorService;
import com.example.pixpalapp.service.PaletteService;
import com.example.pixpalapp.service.TagService;
import com.example.pixpalapp.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
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
    private final PaletteRepository paletteRepository;

    @GetMapping
    public GetPalettesResponse handleGetPalettes(GetPalettesRequest request) {
        GetPalettesDto dto = GetPalettesDto.builder()
                .search(request.getSearch())
                .build();

        List<Palette> palettes = paletteService.getPalettes(dto);

        return new GetPalettesResponse(palettes);
    }

    @GetMapping("/{id}")
    public GetPaletteResponse handleGetPalette(@PathVariable Long id) {
        Palette palette = paletteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Palette not found"));

        return new GetPaletteResponse(palette.getName());
    }

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

        paletteService.createPalette(paletteDto, user, colors, tags);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<String> handleUpdatePalette(@PathVariable Long id, @RequestBody @Valid PaletteUpdateRequest request) {
        PaletteDto paletteDto = PaletteDto.builder()
                .name(request.getName())
                .colorsCodes(request.getColorsCodes())
                .tagsNames(request.getTagsNames())
                .isPublic(request.isPublic())
                .build();

        List<Color> colors = colorService.createColors(paletteDto.getColorsCodes());
        List<Tag> tags = tagService.createTags(paletteDto.getTagsNames());

        Palette palette = paletteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Palette not found"));

        paletteService.updatePalette(palette, paletteDto, colors, tags);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/favorite")
    public ResponseEntity<String> handleToggleFavorite(@PathVariable Long id, @NotNull Principal principal) throws IOException {
        User user = userService.getUserFromPrincipal(principal);
        PaletteDto paletteDto = PaletteDto.builder()
                        .id(id)
                        .build();
        userService.toggleFavoritePalette(user, paletteDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/favorite")
    public GetPalettesResponse handleGetFavorite(@NotNull Principal principal) throws AccessDeniedException {
        User user = userService.getUserFromPrincipal(principal);
        List<Palette> palettes = user.getFavoritePalettes();

        return new GetPalettesResponse(palettes);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> handleDeletePalette(@PathVariable Long id) {
        Palette palette = paletteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Palette not found"));

        paletteRepository.delete(palette);

        return ResponseEntity.ok("Palette deleted successfully");
    }
}