package com.example.pixpalapp.payload.Response.Palette;

import com.example.pixpalapp.entity.Palette;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetPalettesResponse {
    List<Palette> palettes;
}
