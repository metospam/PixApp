package com.example.pixpalapp.service;

import com.example.pixpalapp.entity.Color;
import com.example.pixpalapp.entity.Palette;
import com.example.pixpalapp.entity.Tag;
import com.example.pixpalapp.entity.User;

import java.util.List;

public interface PaletteService {
    Palette createPalette(String name, User user, List<Color> colors, List<Tag> tags);
}
