package com.example.pixpalapp.service.Impl;

import com.example.pixpalapp.dto.Palette.GetPalettesDto;
import com.example.pixpalapp.dto.Palette.PaletteDto;
import com.example.pixpalapp.entity.Color;
import com.example.pixpalapp.entity.Palette;
import com.example.pixpalapp.entity.Tag;
import com.example.pixpalapp.entity.User;
import com.example.pixpalapp.repository.PaletteRepository;
import com.example.pixpalapp.service.PaletteService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class PaletteServiceImpl implements PaletteService {

    PaletteRepository paletteRepository;

    @Override
    public void createPalette(PaletteDto paletteDto, User user, List<Color> colors, List<Tag> tags) {
        Palette palette = new Palette();

        palette.setUser(user);
        palette.setName(paletteDto.getName());
        palette.setColors(colors);
        palette.setTags(tags);

        paletteRepository.save(palette);
    }

    @Override
    public void updatePalette(Palette palette, PaletteDto paletteDto, List<Color> colors, List<Tag> tags) {
        palette.setName(paletteDto.getName());
        palette.setColors(colors);
        palette.setTags(tags);
        palette.setPublic(paletteDto.isPublic());

        paletteRepository.save(palette);
    }

    @Override
    public List<Palette> getPalettes(GetPalettesDto dto) {
        return paletteRepository.findByName(dto.getSearch());
    }
}
