package org.example.midterm.service;

import org.example.midterm.dto.movie.MovieAddRequest;
import org.example.midterm.dto.movie.MovieResponse;

import java.util.List;

public interface MovieService {
    void addMovie(MovieAddRequest request);

    List<MovieResponse> getAll(String s);

    void buy(Long bookId);

    List<MovieResponse> getMoviesByCustomer(String string);
}