package org.example.midterm.controller;

import org.example.midterm.dto.movie.MovieAddRequest;
import org.example.midterm.dto.movie.MovieResponse;
import org.example.midterm.service.MovieService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoBeans;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;
import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
@MockitoBeans({
})
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MovieService movieService;

    MovieControllerTest(MovieService movieService) {
        this.movieService = movieService;
    }

    @Test
    void addMovie() throws Exception {
        MovieAddRequest request = new MovieAddRequest();
        mockMvc.perform(MockMvcRequestBuilders.post("/movie/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Inception\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void movieResponses() throws Exception {
        Mockito.when(movieService.getAll(Mockito.anyString())).thenReturn(List.of(new MovieResponse()));
        mockMvc.perform(MockMvcRequestBuilders.get("/movie/movies")
                .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}