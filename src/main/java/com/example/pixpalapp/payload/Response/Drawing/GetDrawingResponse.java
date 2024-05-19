package com.example.pixpalapp.payload.Response.Drawing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class GetDrawingResponse {
    String name;
    String imageBase64;
}
