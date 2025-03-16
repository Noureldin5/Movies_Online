package org.example.midterm.dto.movie;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieAddRequest {
    private String name;
    private String author_full_name;
    private String transcript;
    private Integer price;
    private Integer age_access;
    private String type;
}