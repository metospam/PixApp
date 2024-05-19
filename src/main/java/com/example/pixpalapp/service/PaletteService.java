package com.example.pixpalapp.service;

import com.example.pixpalapp.dto.Palette.GetPalettesDto;
import com.example.pixpalapp.dto.Palette.PaletteDto;
import com.example.pixpalapp.entity.Color;
import com.example.pixpalapp.entity.Palette;
import com.example.pixpalapp.entity.Tag;
import com.example.pixpalapp.entity.User;

import java.util.List;

public interface PaletteService {
    void createPalette(PaletteDto paletteDto, User user, List<Color> colors, List<Tag> tags);
    void updatePalette(Palette palette, PaletteDto paletteDto, List<Color> colors, List<Tag> tags);
    List<Palette> getPalettes(GetPalettesDto dto);
}
