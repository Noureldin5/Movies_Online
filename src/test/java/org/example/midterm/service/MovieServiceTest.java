package org.example.midterm.service;

import org.example.midterm.dto.movie.MovieAddRequest;
import org.example.midterm.entities.Movie;
import org.example.midterm.entities.Type;
import org.example.midterm.repositories.MovieRepository;
import org.example.midterm.repositories.TypeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.ConstraintViolationException;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MovieServiceTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Test
    void testInvalidMovie() {
        Type type = new Type("Action");
        typeRepository.save(type);

        Movie movie = new Movie("", "A mind-bending thriller", 2010, type);
        assertThrows(ConstraintViolationException.class, () -> {
            movieRepository.save(movie);
        });
    }
}