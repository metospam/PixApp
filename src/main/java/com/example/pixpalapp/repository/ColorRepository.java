package com.example.pixpalapp.repository;

import com.example.pixpalapp.entity.Color;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColorRepository extends CrudRepository<Color, Long> {
    Optional<Color> findByCode(String colorCode);
}
