package org.example.midterm.dto.movie;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MovieResponse {
    private Long id;

    private String title;
    private String author_full_name;
    private String transcript;
    private int created_at;
    private Integer price;
    private Integer age_access;
    private Boolean exist;
    private String type;
}