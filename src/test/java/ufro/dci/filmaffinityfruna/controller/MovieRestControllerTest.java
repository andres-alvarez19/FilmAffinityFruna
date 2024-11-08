package ufro.dci.filmaffinityfruna.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ufro.dci.filmaffinityfruna.model.entity.MovieEntity;
import ufro.dci.filmaffinityfruna.model.entity.DirectorEntity;
import ufro.dci.filmaffinityfruna.model.entity.GenreEntity;
import ufro.dci.filmaffinityfruna.service.MovieService;
import ufro.dci.filmaffinityfruna.utils.LocalDateAdapter;
import ufro.dci.filmaffinityfruna.utils.LocalTimeAdapter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(MovieRestController.class)
class MovieRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    private Gson gson;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new MovieRestController(movieService)).build();
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                .create();
    }

    @Test
    void testSearchMoviesByName() throws Exception {
        String name = "Inception";
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setName(name);

        when(movieService.searchByName(name)).thenReturn(List.of(movieEntity));

        mockMvc.perform(get("/movie/search")
                        .param("name", name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(name));

        verify(movieService, times(1)).searchByName(name);
    }

    @Test
    void testRegister() throws Exception {
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setName("Interstellar");
        movieEntity.setDirector(new DirectorEntity());
        movieEntity.setDuration(LocalTime.of(2,49,0));
        movieEntity.setReleaseYear(LocalDate.of(2014, 11, 7));
        movieEntity.setGenre(new GenreEntity());

        doNothing().when(movieService).register(any(MovieEntity.class));

        String movieJson = gson.toJson(movieEntity);

        mockMvc.perform(post("/movie/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(movieJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Película registrada correctamente"));

        verify(movieService, times(1)).register(any(MovieEntity.class));
    }

    @Test
    void testUpdate() throws Exception {
        long id = 1L;
        MovieEntity updatedMovie = new MovieEntity();
        updatedMovie.setName("The Dark Knight");
        updatedMovie.setDirector(new DirectorEntity());
        updatedMovie.setDuration(LocalTime.of(2,49,0));
        updatedMovie.setReleaseYear(LocalDate.of(2008, 7, 18));
        updatedMovie.setGenre(new GenreEntity());

        doNothing().when(movieService).update(anyLong(), any(MovieEntity.class));

        String movieJson = gson.toJson(updatedMovie);

        mockMvc.perform(put("/movie/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(movieJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Película actualizada correctamente"));

        verify(movieService, times(1)).update(anyLong(), any(MovieEntity.class));
    }

    @Test
    void testDeleteMovieById() throws Exception {
        long id = 1L;

        doNothing().when(movieService).deleteMovieById(id);

        mockMvc.perform(delete("/movie/delete/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Película eliminada correctamente"));

        verify(movieService, times(1)).deleteMovieById(id);
    }
}