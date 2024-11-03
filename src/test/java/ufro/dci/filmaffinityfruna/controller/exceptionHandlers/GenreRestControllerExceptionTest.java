package ufro.dci.filmaffinityfruna.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
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
        // Simular que el servicio lanza una excepción genérica
        Mockito.when(genreService.searchByName("Action")).thenThrow(new RuntimeException("Error genérico"));

        // Realizar una petición GET y verificar la respuesta
        mockMvc.perform(get("/genre")
                        .param("name", "Action")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Ocurrió un error inesperado: Error genérico"));
    }

    @Test
    void testHandleDatabaseException() throws Exception {
        // Simular que el servicio lanza una excepción de violación de integridad
        doThrow(new DataIntegrityViolationException("Violación de clave única"))
                .when(genreService).register(Mockito.any(GenreEntity.class));

        // Realizar una petición POST y verificar la respuesta
        mockMvc.perform(post("/genre")
                        .content("{\"name\": \"Drama\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Error de integridad en la base de datos: Violación de clave única"));
    }

    @Test
    void testHandleValidationException() throws Exception {
        mockMvc.perform(post("/genre")
                .content("{}") // JSON vacío para provocar error de validación
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").exists());
    }

}
