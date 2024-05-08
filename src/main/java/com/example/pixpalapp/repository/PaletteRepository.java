package com.example.pixpalapp.repository;

import com.example.pixpalapp.entity.Palette;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaletteRepository extends CrudRepository<Palette, Long> {
}
