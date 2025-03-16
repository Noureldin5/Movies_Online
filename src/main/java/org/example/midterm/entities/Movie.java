package org.example.midterm.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author_full_name;
    private String transcript;
    private LocalDateTime created_at;
    private Integer price;
    private Integer ageAccess;
    private Boolean exist = true;

    public Movie(String title, String transcript, LocalDateTime created_at, Type type) {
        this.title = title;
        this.transcript = transcript;
        this.created_at = created_at;
        this.type = type;
    }

    @ManyToOne()
    private Type type;

    @ManyToOne()
    private Customer customer;



}