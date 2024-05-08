package com.example.pixpalapp.service;


import com.example.pixpalapp.dto.DrawingDto;
import com.example.pixpalapp.entity.Drawing;
import com.example.pixpalapp.entity.User;

public interface DrawingService {
    Drawing createDrawing(DrawingDto dto, User user);
    Drawing updateDrawing(Drawing drawing, DrawingDto dto);
}
