package ufro.dci.filmaffinityfruna.controller.exception_handlers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ufro.dci.filmaffinityfruna.controller.MovieRestController;
import ufro.dci.filmaffinityfruna.model.entity.MovieEntity;
import ufro.dci.filmaffinityfruna.service.MovieService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.doThrow;

@WebMvcTest(MovieRestController.class)
class MovieRestControllerExceptionTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @Test
    void testHandleGeneralException() throws Exception {
        Mockito.when(movieService.searchByName("Inception")).thenThrow(new RuntimeException("Error genérico"));

        mockMvc.perform(get("/movie/search")
                        .param("name", "Inception")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Ocurrió un error inesperado: Error genérico"));
    }

    @Test
    void testHandleDatabaseException() throws Exception {
        doThrow(new DataIntegrityViolationException("Violación de clave única"))
                .when(movieService).register(Mockito.any(MovieEntity.class));

        mockMvc.perform(post("/movie/register")
                        .content("{\"name\": \"Interstellar\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Error de integridad en la base de datos: Violación de clave única"));
    }

    @Test
    void testHandleValidationException() throws Exception {
        mockMvc.perform(post("/movie/register")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").exists());
    }
}