package ufro.dci.filmaffinityfruna.controller.exceptionHandlers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ufro.dci.filmaffinityfruna.controller.DirectorRestController;
import ufro.dci.filmaffinityfruna.model.entity.DirectorEntity;
import ufro.dci.filmaffinityfruna.service.DirectorService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.doThrow;

@WebMvcTest(DirectorRestController.class)
class DirectorRestControllerExceptionTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DirectorService directorService;

    @Test
    void testHandleGeneralException() throws Exception {
        Mockito.when(directorService.searchByName("Nolan")).thenThrow(new RuntimeException("Error genérico"));

        mockMvc.perform(get("/director/search/Nolan")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Ocurrió un error inesperado: Error genérico"));
    }

    @Test
    void testHandleDatabaseException() throws Exception {
        doThrow(new DataIntegrityViolationException("Violación de clave única"))
                .when(directorService).register(Mockito.any(DirectorEntity.class));

        mockMvc.perform(post("/director/register")
                        .content("{\"name\": \"Christopher Nolan\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Error de integridad en la base de datos: Violación de clave única"));
    }

    @Test
    void testHandleValidationException() throws Exception {
        mockMvc.perform(post("/director/register")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").exists());
    }
}