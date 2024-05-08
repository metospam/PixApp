package com.example.pixpalapp.payload.Request;

import com.example.pixpalapp.validator.annotaion.ArrayNotEmpty;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaletteCreateRequest {
    @NotBlank
    String name;

    @ArrayNotEmpty
    List<String> colorsCodes;

    @ArrayNotEmpty
    List<String> tagsNames;
}
