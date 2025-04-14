package org.example.midterm.service.impl;

import jakarta.validation.Valid;
import org.example.midterm.dto.movie.MovieAddRequest;
import org.example.midterm.dto.movie.MovieResponse;
import org.example.midterm.entities.Customer;
import org.example.midterm.entities.Movie;
import org.example.midterm.entities.Type;
import org.example.midterm.entities.User;
import org.example.midterm.exception.NotFoundException;
import org.example.midterm.mapper.MovieMapper;
import org.example.midterm.repositories.MovieRepository;
import org.example.midterm.repositories.TypeRepository;
import org.example.midterm.repositories.UserRepository;
import org.example.midterm.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final TypeRepository typeRepository;
    private final MovieMapper movieMapper;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void addMovie(@Valid MovieAddRequest request) {
        if (movieRepository.findByTranscript(request.getTranscript()).isPresent())
            throw new NotFoundException("movie with this transcript is already exist!: "+request.getTranscript(),
                    HttpStatus.BAD_REQUEST);

        Movie movie = new Movie();

        movie.setTitle(request.getTitle());
        movie.setPrice(request.getPrice());
        movie.setAgeAccess(request.getAge_access());
        movie.setAuthor_full_name(request.getAuthor_full_name());
        movie.setTranscript(request.getTranscript());
        movie.setCreated_at(request.getCreated_at());
        movie.setExist(true);
        Optional<Type> type = typeRepository.findByName(request.getType());
        if (type.isEmpty())
            throw new NotFoundException("no type with name: "+request.getType(), HttpStatus.BAD_REQUEST);
        movie.setType(type.get());
        movieRepository.save(movie);
    }

    @Override
    public List<MovieResponse> getAll(String s) {
        return movieMapper.toDtoS(movieRepository.findAll());
    }

    @Override
    public void buy(Long movieId) {
        Optional<Movie> optionalMovie = movieRepository.findById(movieId);
        if (optionalMovie.isEmpty())
            throw new NotFoundException("no movie with id: "+movieId, HttpStatus.BAD_REQUEST);
        Movie movie = optionalMovie.get();
        movie.setExist(false);
        movieRepository.save(movie);
        User user = userRepository.findByName("user").
                orElseThrow(() -> new NotFoundException("no user with name: user", HttpStatus.BAD_REQUEST));
        userRepository.save(user);
    }

    @Override
    public List<MovieResponse> getMoviesByCustomer(String string) {
        Optional<Customer> customer = userRepository.findByName(string)
                .map(User::getCustomer);
        if (customer.isPresent()){
            List<Movie> movies = customer.get().getMovies();
            return movieMapper.toDtoS(movies);
        }
        return null;
    }
}