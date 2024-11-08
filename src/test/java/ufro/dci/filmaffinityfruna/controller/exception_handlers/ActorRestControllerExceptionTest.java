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
import ufro.dci.filmaffinityfruna.controller.ActorRestController;
import ufro.dci.filmaffinityfruna.model.entity.ActorEntity;
import ufro.dci.filmaffinityfruna.service.ActorService;
import ufro.dci.filmaffinityfruna.utils.LocalDateAdapter;
import ufro.dci.filmaffinityfruna.utils.LocalTimeAdapter;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.doThrow;

@WebMvcTest(ActorRestController.class)
class ActorRestControllerExceptionTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActorService actorService;

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
        when(actorService.searchByName("Robert Downey Jr.")).thenThrow(new RuntimeException("Error genérico"));
        ActorEntity actorEntity = new ActorEntity();
        actorEntity.setName("Robert Downey Jr.");

        mockMvc.perform(get("/actor/search")
                        .content(gson.toJson(actorEntity))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testHandleDatabaseException() throws Exception {
        doThrow(new DataIntegrityViolationException("Violación de clave única"))
                .when(actorService).register(Mockito.any(ActorEntity.class));

        ActorEntity actorEntity = new ActorEntity();
        actorEntity.setName("Chris Hemsworth");
        actorEntity.setNationality("Australian");
        actorEntity.setDateOfBirth(LocalDate.of(1983, 8, 11));

        String actorJson = gson.toJson(actorEntity);

        mockMvc.perform(post("/actor/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(actorJson))
                .andExpect(status().isConflict());
    }

    @Test
    void testHandleValidationException() throws Exception {
        mockMvc.perform(post("/actor/register")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}