package com.example.pixpalapp.service;


import com.example.pixpalapp.dto.Drawing.DrawingDto;
import com.example.pixpalapp.entity.Drawing;
import com.example.pixpalapp.entity.User;

import java.io.IOException;
import java.util.List;

public interface DrawingService {
    void createDrawing(DrawingDto dto, User user) throws IOException;
    void updateDrawing(Drawing drawing, DrawingDto dto);
    List<Drawing> getDrawings(List<String> tagNames, String search);
}
