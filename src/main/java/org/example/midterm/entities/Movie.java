package org.example.midterm.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String author_full_name;
    private String transcript;
    private String created_date;
    private Integer price;
    private Integer ageAccess;
    private Boolean exist = true;

    @ManyToOne()
    private Type type;

    @ManyToOne()
    private Customer customer;



}