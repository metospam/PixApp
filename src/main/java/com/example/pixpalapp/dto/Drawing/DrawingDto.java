package com.example.pixpalapp.dto.Drawing;

import com.example.pixpalapp.model.Cell;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DrawingDto {
    Long id;
    MultipartFile image;
    String name;
    int x;
    int y;
    List<Cell> cells;
    boolean isPublic;
}