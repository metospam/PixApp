package com.example.pixpalapp.payload.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
public class DrawingResponse {

    Long id;
    String name;
    int x;
    int y;
    List<HashMap<String, String>> cells;

}
