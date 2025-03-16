package org.example.midterm.integration;

import org.example.midterm.entities.Movie;
import org.example.midterm.entities.Type;
import org.example.midterm.repositories.MovieRepository;
import org.example.midterm.repositories.TypeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MovieIntegrationTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Test
    void testMovieCreation() {
        Type type = new Type("Action");
        typeRepository.save(type);

        Movie movie = new Movie("Inception", "A mind-bending thriller", 2010, type);
        movieRepository.save(movie);

        List<Movie> movies = movieRepository.findAll();
        assertEquals(1, movies.size());
        assertEquals("Inception", movies.get(0).getTitle());
    }
}