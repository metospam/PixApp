package com.example.pixpalapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "palettes")
@Getter
@Setter
@NoArgsConstructor
public class Palette {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    String name;

    @ManyToOne
    @JsonBackReference
    private User user;

    @ManyToMany
    @JoinTable(
            name = "palettes_colors",
            joinColumns = @JoinColumn(name = "palette_id"),
            inverseJoinColumns = @JoinColumn(name = "color_id")
    )
    private List<Color> colors;

    @ManyToMany
    @JoinTable(
            name = "palettes_tags",
            joinColumns = @JoinColumn(name = "palette_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    List<Tag> tags;
}
