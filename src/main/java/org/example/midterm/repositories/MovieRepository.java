package org.example.midterm.repositories;

import org.example.midterm.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByTranscript(String tra);
    List<Movie> findAllByExistAndAgeAccessGreaterThan(Boolean b, Integer integer);
    List<Movie> findAllByExistAndAgeAccessLessThan(Boolean b, Integer integer);
}