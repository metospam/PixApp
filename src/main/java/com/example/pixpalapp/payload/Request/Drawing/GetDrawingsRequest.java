package com.example.pixpalapp.payload.Request.Drawing;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetDrawingsRequest {
    List<String> tagNames;
    String search;
}
