package com.example.pixpalapp.payload.Request.Drawing;

import com.example.pixpalapp.model.Cell;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DrawingCreateRequest {

    @NotBlank
    String name;

    @NotEmpty
    List<Cell> cells;
}
