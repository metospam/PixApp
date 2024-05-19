package com.example.pixpalapp.payload.Request.Palette;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaletteUpdateRequest {
    String name;
    boolean isPublic;
    List<String> colorsCodes;
    List<String> tagsNames;
}
