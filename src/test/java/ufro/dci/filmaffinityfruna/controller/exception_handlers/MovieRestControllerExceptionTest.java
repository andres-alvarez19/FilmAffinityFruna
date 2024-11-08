package ufro.dci.filmaffinityfruna.controller.exception_handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ufro.dci.filmaffinityfruna.controller.MovieRestController;
import ufro.dci.filmaffinityfruna.model.entity.DirectorEntity;
import ufro.dci.filmaffinityfruna.model.entity.GenreEntity;
import ufro.dci.filmaffinityfruna.model.entity.MovieEntity;
import ufro.dci.filmaffinityfruna.service.MovieService;
import ufro.dci.filmaffinityfruna.utils.LocalDateAdapter;
import ufro.dci.filmaffinityfruna.utils.LocalTimeAdapter;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.doThrow;

@WebMvcTest(MovieRestController.class)
class MovieRestControllerExceptionTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    private Gson gson;

    @BeforeEach
    void setUp() {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                .create();
    }

    @Test
    void testHandleGeneralException() throws Exception {
        Mockito.when(movieService.searchByName("Inception")).thenThrow(new RuntimeException("Error genérico"));
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setName("Inception");

        mockMvc.perform(get("/movie/search")
                        .content(gson.toJson(movieEntity))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testHandleDatabaseException() throws Exception {
        doThrow(new DataIntegrityViolationException("Violación de clave única"))
                .when(movieService).register(Mockito.any(MovieEntity.class));

        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setName("Interstellar");
        movieEntity.setReleaseYear(LocalDate.of(2014, 11, 7));
        movieEntity.setDuration(LocalTime.of(2, 49));
        movieEntity.setDirector(new DirectorEntity());
        movieEntity.setGenre(new GenreEntity());

        String movieJson = gson.toJson(movieEntity);

        mockMvc.perform(post("/movie/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(movieJson))
                .andExpect(status().isConflict());
    }

    @Test
    void testHandleValidationException() throws Exception {
        mockMvc.perform(post("/movie/register")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest());
    }
}