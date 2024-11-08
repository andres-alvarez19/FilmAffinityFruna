package ufro.dci.filmaffinityfruna.controller.exception_handlers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ufro.dci.filmaffinityfruna.controller.UserRestController;
import ufro.dci.filmaffinityfruna.model.entity.UserEntity;
import ufro.dci.filmaffinityfruna.service.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.doThrow;

@WebMvcTest(UserRestController.class)
class UserRestControllerExceptionTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testHandleGeneralException() throws Exception {
        Mockito.when(userService.findById(1)).thenThrow(new RuntimeException("Error genérico"));

        mockMvc.perform(get("/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Ocurrió un error inesperado: Error genérico"));
    }

    @Test
    void testHandleDatabaseException() throws Exception {
        doThrow(new DataIntegrityViolationException("Violación de clave única"))
                .when(userService).register(Mockito.any(UserEntity.class));

        mockMvc.perform(post("/user/register")
                        .content("{\"username\": \"johndoe\", \"password\": \"password123\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Error de integridad en la base de datos: Violación de clave única"));
    }

    @Test
    void testHandleValidationException() throws Exception {
        mockMvc.perform(post("/user/register")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.username").exists());
    }
}