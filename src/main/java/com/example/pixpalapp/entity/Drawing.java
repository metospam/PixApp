package com.example.pixpalapp.entity;

import com.example.pixpalapp.converter.CellListConverter;
import com.example.pixpalapp.model.Cell;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "is_public")
    private boolean isPublic;

    @Lob
    @JsonIgnore
    @Column
    private byte[] image;

    @ManyToOne
    private User user;

    @ManyToMany
    @JoinTable(
            name = "drawings_tags",
            joinColumns = @JoinColumn(name = "drawing_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    List<Tag> tags;

    @Convert(converter = CellListConverter.class)
    @Column(columnDefinition = "json")
    private List<Cell> cells;
}
