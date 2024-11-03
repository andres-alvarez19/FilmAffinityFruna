package ufro.dci.filmaffinityfruna.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ufro.dci.filmaffinityfruna.model.entity.GenreEntity;
import ufro.dci.filmaffinityfruna.service.GenreService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(GenreRestController.class)
class GenreRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreService genreService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new GenreRestController(genreService)).build();
    }

    @Test
    void testSearchByName() throws Exception {
        String name = "Action";
        GenreEntity genreEntity = new GenreEntity();
        genreEntity.setName(name);

        when(genreService.searchByName(name)).thenReturn(genreEntity);

        mockMvc.perform(get("/genre/search")
                .param("name", name)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name));

        verify(genreService, times(1)).searchByName(name);
    }

    @Test
    void testRegister() throws Exception {
        GenreEntity genreEntity = new GenreEntity();
        genreEntity.setName("Drama");

        doNothing().when(genreService).register(any(GenreEntity.class));

        mockMvc.perform(post("/genre/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Drama\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Género registrado correctamente"));

        verify(genreService, times(1)).register(any(GenreEntity.class));
    }

    @Test
    void testUpdate() throws Exception {
        String name = "Comedy";
        GenreEntity updatedGenre = new GenreEntity();
        updatedGenre.setName(name);

        doNothing().when(genreService).update(anyString(), any(GenreEntity.class));

        mockMvc.perform(put("/genre/update/{name}", name)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Comedy\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Género actualizado correctamente"));

        verify(genreService, times(1)).update(anyString(), any(GenreEntity.class));
    }

    @Test
    void testDeleteGenreByName() throws Exception {
        String name = "Horror";

        doNothing().when(genreService).deleteGenreByName(name);

        mockMvc.perform(delete("/genre/delete/{name}", name)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Género eliminado correctamente"));

        verify(genreService, times(1)).deleteGenreByName(name);
    }
}