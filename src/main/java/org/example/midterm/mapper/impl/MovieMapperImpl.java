package org.example.midterm.mapper.impl;

import org.example.midterm.dto.movie.MovieResponse;
import org.example.midterm.entities.Movie;
import org.example.midterm.mapper.MovieMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MovieMapperImpl implements MovieMapper {
    @Override
    public List<MovieResponse> toDtoS(List<Movie> all) {
        List<MovieResponse> movieResponses = new ArrayList<>();
        for (Movie movie: all){
            movieResponses.add(toDto(movie));
        }
        return movieResponses;
    }

    @Override
    public MovieResponse toDto(Movie movie) {
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.setId(movie.getId());
        movieResponse.setTitle(movie.getTitle());
        movieResponse.setExist(movie.getExist());
        movieResponse.setTranscript(movie.getTranscript());
        movieResponse.setAge_access(movie.getAgeAccess());
        movieResponse.setPrice(movie.getPrice());
        movieResponse.setCreated_at(movie.getCreated_at());
        movieResponse.setAuthor_full_name(movie.getAuthor_full_name());
        movieResponse.setType(movie.getType().getName());
        return movieResponse;
    }
}