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
import ufro.dci.filmaffinityfruna.model.entity.DirectorEntity;
import ufro.dci.filmaffinityfruna.service.DirectorService;
import ufro.dci.filmaffinityfruna.utils.LocalDateAdapter;
import ufro.dci.filmaffinityfruna.utils.LocalTimeAdapter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(DirectorRestController.class)
class DirectorRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DirectorService directorService;

    private Gson gson;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new DirectorRestController(directorService)).build();
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                .create();
    }

    @Test
    void testSearchByName() throws Exception {
        String name = "Christopher Nolan";
        DirectorEntity directorEntity = new DirectorEntity();
        directorEntity.setName(name);

        when(directorService.searchByName(name)).thenReturn(List.of(directorEntity));

        mockMvc.perform(get("/director/search/{name}", name)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(name));

        verify(directorService, times(1)).searchByName(name);
    }

    @Test
    void testRegister() throws Exception {
        DirectorEntity directorEntity = new DirectorEntity();
        directorEntity.setName("Quentin Tarantino");
        directorEntity.setDateOfBirth(LocalDate.of(1963, 3, 27));
        directorEntity.setNationality("American");

        doNothing().when(directorService).register(any(DirectorEntity.class));

        String directorJson = gson.toJson(directorEntity);

        mockMvc.perform(post("/director/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(directorJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Director registrado correctamente"));

        verify(directorService, times(1)).register(any(DirectorEntity.class));
    }

    @Test
    void testUpdate() throws Exception {
        long id = 1L;
        DirectorEntity updatedDirector = new DirectorEntity();
        updatedDirector.setName("Steven Spielberg");

        doNothing().when(directorService).update(anyLong(), any(DirectorEntity.class));

        String directorJson = gson.toJson(updatedDirector);

        mockMvc.perform(put("/director/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(directorJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Director actualizado correctamente"));

        verify(directorService, times(1)).update(anyLong(), any(DirectorEntity.class));
    }

    @Test
    void testDeleteDirectorById() throws Exception {
        long id = 1L;

        doNothing().when(directorService).deleteDirectorById(id);

        mockMvc.perform(delete("/director/delete/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Director eliminado correctamente"));

        verify(directorService, times(1)).deleteDirectorById(id);
    }
}