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
import ufro.dci.filmaffinityfruna.controller.GenreRestController;
import ufro.dci.filmaffinityfruna.model.entity.GenreEntity;
import ufro.dci.filmaffinityfruna.service.GenreService;
import ufro.dci.filmaffinityfruna.utils.LocalDateAdapter;
import ufro.dci.filmaffinityfruna.utils.LocalTimeAdapter;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.doThrow;

@WebMvcTest(GenreRestController.class)
class GenreRestControllerExceptionTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreService genreService;

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
        Mockito.when(genreService.searchByName("Action")).thenThrow(new RuntimeException("Error genérico"));
        GenreEntity genreEntity = new GenreEntity();
        genreEntity.setName("Action");

        mockMvc.perform(get("/genre/search")
                        .content(gson.toJson(genreEntity))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testHandleDatabaseException() throws Exception {
        doThrow(new DataIntegrityViolationException("Violación de clave única"))
                .when(genreService).register(Mockito.any(GenreEntity.class));

        GenreEntity genreEntity = new GenreEntity();
        genreEntity.setName("Drama");

        String genreJson = gson.toJson(genreEntity);

        mockMvc.perform(post("/genre/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(genreJson))
                .andExpect(status().isConflict());
    }

    @Test
    void testHandleValidationException() throws Exception {
        mockMvc.perform(post("/genre/register")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}