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
import ufro.dci.filmaffinityfruna.model.entity.GenreEntity;
import ufro.dci.filmaffinityfruna.service.GenreService;
import ufro.dci.filmaffinityfruna.utils.LocalDateAdapter;
import ufro.dci.filmaffinityfruna.utils.LocalTimeAdapter;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(GenreRestController.class)
class GenreRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreService genreService;

    private Gson gson;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new GenreRestController(genreService)).build();
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                .create();

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

        String genreJson = gson.toJson(genreEntity);

        mockMvc.perform(post("/genre/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(genreJson))
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

        String genreJson = gson.toJson(updatedGenre);

        mockMvc.perform(put("/genre/update/{name}", name)
                .contentType(MediaType.APPLICATION_JSON)
                .content(genreJson))
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