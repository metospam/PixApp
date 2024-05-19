package com.example.pixpalapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Component
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @JsonIgnore
    @Column
    private byte[] image;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    @Column
    @JsonIgnore
    private String password;

    @ManyToOne
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Drawing> drawings;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "users_favorite_drawings",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "drawing_id")
    )
    private List<Drawing> favoriteDrawings;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Palette> palettes;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "users_favorite_palettes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "palette_id")
    )
    private List<Palette> favoritePalettes;
}
