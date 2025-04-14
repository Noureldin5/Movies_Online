package org.example.midterm.dto.movie;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MovieAddRequest {
    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Author full name is mandatory")
    private String author_full_name;

    @NotBlank(message = "Transcript is mandatory")
    private String transcript;

    @NotNull(message = "Created at is mandatory")
    private int created_at;

    @NotNull(message = "Price is mandatory")
    private Integer price;

    @NotNull(message = "Age access is mandatory")
    private Integer age_access;

    @NotBlank(message = "Type is mandatory")
    private String type;
}