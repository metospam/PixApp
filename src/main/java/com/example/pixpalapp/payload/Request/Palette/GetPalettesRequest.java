package com.example.pixpalapp.payload.Request.Palette;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetPalettesRequest {
    String search;
}
