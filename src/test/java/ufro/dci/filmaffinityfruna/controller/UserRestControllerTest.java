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
import ufro.dci.filmaffinityfruna.model.entity.UserEntity;
import ufro.dci.filmaffinityfruna.service.UserService;
import ufro.dci.filmaffinityfruna.utils.LocalDateAdapter;
import ufro.dci.filmaffinityfruna.utils.LocalTimeAdapter;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(UserRestController.class)
class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private Gson gson;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new UserRestController(userService)).build();
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                .create();
    }

    @Test
    void testRegister() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("john_doe");
        userEntity.setPassword("password123");
        userEntity.setEmail("example@ufromail.cl");


        doNothing().when(userService).register(any(UserEntity.class));

        String userJson = gson.toJson(userEntity);

        mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Usuario registrado correctamente"));

        verify(userService, times(1)).register(any(UserEntity.class));
    }

    @Test
    void testGetUser() throws Exception {
        long id = 1L;
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setUsername("john_doe");

        when(userService.findById(id)).thenReturn(userEntity);

        mockMvc.perform(get("/user/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.username").value("john_doe"));

        verify(userService, times(1)).findById(id);
    }
}