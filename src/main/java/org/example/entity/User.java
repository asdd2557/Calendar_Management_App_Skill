package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    private String email;
    private String password;
    private String profile_picture;
    private String bio;
    private boolean is_private;
    private LocalDate create_at;
    private LocalDate updated_at;

}
