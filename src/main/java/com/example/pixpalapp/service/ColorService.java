package com.example.pixpalapp.service;

import com.example.pixpalapp.entity.Color;

import java.util.List;

public interface ColorService {
    List<Color> createColors(List<String> colorsCodes);
    Color createColor(String colorCode);
}
