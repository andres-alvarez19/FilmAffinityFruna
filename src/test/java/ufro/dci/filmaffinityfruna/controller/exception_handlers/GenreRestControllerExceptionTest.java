package ufro.dci.filmaffinityfruna.controller.exception_handlers;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.doThrow;

@WebMvcTest(GenreRestController.class)
class GenreRestControllerExceptionTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreService genreService;

    @Test
    void testHandleGeneralException() throws Exception {
        Mockito.when(genreService.searchByName("Action")).thenThrow(new RuntimeException("Error genérico"));

        mockMvc.perform(get("/genre/search")
                        .param("name", "Action")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Ocurrió un error inesperado: Error genérico"));
    }

    @Test
    void testHandleDatabaseException() throws Exception {
        doThrow(new DataIntegrityViolationException("Violación de clave única"))
                .when(genreService).register(Mockito.any(GenreEntity.class));

        mockMvc.perform(post("/genre/register")
                        .content("{\"name\": \"Drama\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Error de integridad en la base de datos: Violación de clave única"));
    }

    @Test
    void testHandleValidationException() throws Exception {
        mockMvc.perform(post("/genre/register")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").exists());
    }

}
