package com.example.pixpalapp.payload.Request.Drawing;

import com.example.pixpalapp.model.Cell;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DrawingUpdateRequest {
    String name;
    List<Cell> cells;
    boolean isPublic;
}
