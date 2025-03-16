package org.example.midterm.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotBlank(message = "Author name is mandatory")
    private String author_full_name;
    @NotBlank(message = "Transcript is mandatory")
    private String transcript;
    private int created_at;
    private Integer price;
    private Integer ageAccess;
    private Boolean exist = true;

    public Movie(String title, String transcript, int created_at, Type type) {
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