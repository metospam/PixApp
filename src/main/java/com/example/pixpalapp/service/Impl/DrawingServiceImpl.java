package com.example.pixpalapp.service.Impl;

import com.example.pixpalapp.dto.Drawing.DrawingDto;
import com.example.pixpalapp.entity.Drawing;
import com.example.pixpalapp.entity.Tag;
import com.example.pixpalapp.entity.User;
import com.example.pixpalapp.repository.DrawingRepository;
import com.example.pixpalapp.service.DrawingService;
import com.example.pixpalapp.service.StorageService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class DrawingServiceImpl implements DrawingService {

    @PersistenceContext
    EntityManager entityManager;
    DrawingRepository drawingRepository;
    StorageService storageService;

    @Override
    public void createDrawing(DrawingDto dto, User user) {
        Drawing drawing = new Drawing();

        drawing.setName(dto.getName());
        drawing.setUser(user);
        drawing.setCells(dto.getCells());
        drawing.setPublic(false);

        byte[] imageBytes = storageService.convertCellsToImage(dto.getCells());
        drawing.setImage(imageBytes);

        drawingRepository.save(drawing);
    }

    @Override
    public void updateDrawing(Drawing drawing, DrawingDto dto) {
        drawing.setName(dto.getName());
        drawing.setCells(dto.getCells());
        drawing.setPublic(dto.isPublic());

        drawingRepository.save(drawing);
    }

    public List<Drawing> getDrawings(List<String> tagNames, String search) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Drawing> cq = cb.createQuery(Drawing.class);
        Root<Drawing> drawing = cq.from(Drawing.class);

        Predicate finalPredicate = cb.conjunction();

        if (tagNames != null && !tagNames.isEmpty()) {
            Join<Drawing, Tag> tagJoin = drawing.join("tags");
            Predicate tagPredicate = tagJoin.get("name").in(tagNames);
            finalPredicate = cb.and(finalPredicate, tagPredicate);
        }

        if (search != null && !search.isEmpty()) {
            Predicate searchPredicate = cb.like(cb.lower(drawing.get("name")), "%" + search.toLowerCase() + "%");
            finalPredicate = cb.and(finalPredicate, searchPredicate);
        }

        cq.where(finalPredicate);
        cq.distinct(true);

        return entityManager.createQuery(cq).getResultList();
    }
}
