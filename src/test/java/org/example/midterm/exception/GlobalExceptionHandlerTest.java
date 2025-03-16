package org.example.midterm.exception;

import org.example.midterm.controller.MovieController;
import org.example.midterm.service.MovieService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MovieService movieService;

    GlobalExceptionHandlerTest(MovieService movieService) {
        this.movieService = movieService;
    }

    @Test
    void handleException() throws Exception {
        Mockito.doThrow(new RuntimeException("Test Exception")).when(movieService).addMovie(Mockito.any());

        mockMvc.perform(MockMvcRequestBuilders.post("/movie/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Inception\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Test Exception"));
    }
}