package com.example.pixpalapp.dto;

import com.example.pixpalapp.model.Cell;
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
public class DrawingDto {
    String name;
    int x;
    int y;
    List<Cell> cells;
}
