package com.example.pixpalapp.service.Impl;

import com.example.pixpalapp.dto.DrawingDto;
import com.example.pixpalapp.entity.Drawing;
import com.example.pixpalapp.entity.User;
import com.example.pixpalapp.repository.DrawingRepository;
import com.example.pixpalapp.service.DrawingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class DrawingServiceImpl implements DrawingService {

    DrawingRepository drawingRepository;

    @Override
    public Drawing createDrawing(DrawingDto dto, User user) {
        Drawing drawing = new Drawing();

        drawing.setName(dto.getName());
        drawing.setUser(user);
        drawing.setCells(dto.getCells());

        return drawingRepository.save(drawing);
    }

    @Override
    public Drawing updateDrawing(Drawing drawing, DrawingDto dto) {
        drawing.setName(dto.getName());
        drawing.setCells(dto.getCells());

        return drawingRepository.save(drawing);
    }
}
