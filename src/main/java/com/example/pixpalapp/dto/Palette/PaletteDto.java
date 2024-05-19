package com.example.pixpalapp.dto.Palette;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaletteDto {
    Long id;
    String name;
    List<String> colorsCodes;
    List<String> tagsNames;
    boolean isPublic;
}