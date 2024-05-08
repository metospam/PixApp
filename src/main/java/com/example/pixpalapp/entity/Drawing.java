package com.example.pixpalapp.entity;

import com.example.pixpalapp.converter.CellListConverter;
import com.example.pixpalapp.model.Cell;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "drawings")
@Getter
@Setter
@NoArgsConstructor
public class Drawing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @ManyToOne
    private User user;

    @Convert(converter = CellListConverter.class)
    @Column(columnDefinition = "json")
    private List<Cell> cells;
}
