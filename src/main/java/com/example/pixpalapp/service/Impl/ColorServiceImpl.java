package com.example.pixpalapp.service.Impl;

import com.example.pixpalapp.entity.Color;
import com.example.pixpalapp.repository.ColorRepository;
import com.example.pixpalapp.service.ColorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {

    ColorRepository colorRepository;

    @Override
    public List<Color> createColors(List<String> colorsCodes) {
        return colorsCodes.stream()
                .map(this::createOrGetColor)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public Color createColor(String colorCode){
        Color color = new Color();
        color.setCode(colorCode);

        return colorRepository.save(color);
    }

    private Color createOrGetColor(String colorCode) {
        return colorRepository.findByCode(colorCode)
                .orElseGet(() -> createColor(colorCode));
    }
}
