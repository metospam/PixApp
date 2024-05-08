package com.example.pixpalapp.service.Impl;

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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class PaletteServiceImpl implements PaletteService {

    PaletteRepository paletteRepository;

    @Override
    @Transactional
    public Palette createPalette(String name, User user, List<Color> colors, List<Tag> tags) {
        Palette palette = new Palette();

        palette.setUser(user);
        palette.setName(name);
        palette.setColors(colors);
        palette.setTags(tags);

        return paletteRepository.save(palette);
    }
}
