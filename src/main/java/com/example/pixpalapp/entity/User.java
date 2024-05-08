package com.example.pixpalapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Drawing> drawings;

    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Palette> palettes;
}
