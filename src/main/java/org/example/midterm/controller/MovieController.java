package org.example.midterm.controller;

import org.example.midterm.dto.movie.MovieAddRequest;
import org.example.midterm.dto.movie.MovieResponse;
import org.example.midterm.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/movie")
@AllArgsConstructor
public class MovieController {
    private MovieService movieService;

    @PostMapping("/add")
    public void addMovie(@Valid @RequestBody MovieAddRequest request){
        movieService.addMovie(request);
    }
    @GetMapping("/movies")
    public List<MovieResponse> movieResponses(@RequestHeader("Authorization") String string){
        return movieService.getAll(string);
    }


    @PostMapping("/buy/{movieId}")
    public void buy(@PathVariable Long movieId){
        movieService.buy(movieId);
    }
    @GetMapping("/customer/movies")
    public List<MovieResponse> CustomerMovies(@RequestHeader("Authorization") String string){
        return movieService.getMoviesByCustomer(string);
    }
}