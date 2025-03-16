package org.example.midterm.mapper;

import org.example.midterm.dto.movie.MovieResponse;
import org.example.midterm.entities.Movie;

import java.util.List;

public interface MovieMapper {
    List<MovieResponse> toDtoS(List<Movie> all);

    MovieResponse toDto(Movie movie);
}