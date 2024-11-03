package ufro.dci.filmaffinityfruna.controller;

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

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new DirectorRestController(directorService)).build();
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
        doNothing().when(directorService).register(any(DirectorEntity.class));

        mockMvc.perform(post("/director/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Quentin Tarantino\", \"dateOfBirth\":\"1963-03-27\", \"nationality\":\"American\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Director registrado correctamente"));

        verify(directorService, times(1)).register(any(DirectorEntity.class));
    }

    @Test
    void testUpdate() throws Exception {
        Long id = 1L;

        doNothing().when(directorService).update(anyLong(), any(DirectorEntity.class));

        mockMvc.perform(put("/director/update/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Steven Spielberg\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Director actualizado correctamente"));

        verify(directorService, times(1)).update(anyLong(), any(DirectorEntity.class));
    }

    @Test
    void testDeleteDirectorById() throws Exception {
        Long id = 1L;

        doNothing().when(directorService).deleteDirectorById(id);

        mockMvc.perform(delete("/director/delete/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Director eliminado correctamente"));

        verify(directorService, times(1)).deleteDirectorById(id);
    }
}