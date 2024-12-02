package ufro.dci.filmaffinityfruna.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ufro.dci.filmaffinityfruna.model.entity.UserEntity;
import ufro.dci.filmaffinityfruna.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("usuario");
        userEntity.setEmail("usuario@ejemplo.com");
        userEntity.setPassword("password");
    }

    @Test
    void testRegistrarUsuario_Exito() {
        when(userRepository.existsByEmail(userEntity.getEmail())).thenReturn(false);

        userService.register(userEntity);

        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void testActualizarUsuario_Exito() {
        long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        UserEntity updatedUser = new UserEntity();
        updatedUser.setUsername("updateduser");
        updatedUser.setEmail("updateduser@example.com");

        userService.update(userId, updatedUser);

        assertEquals("updateduser", userEntity.getUsername());
        assertEquals("updateduser@example.com", userEntity.getEmail());
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void testEliminarUsuarioExito() {
        long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);

        userService.deleteUserById(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testBuscarPorNombreExito() {
        String username = "testuser";
        when(userRepository.existsByUsername(username)).thenReturn(true);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));

        UserEntity result = userService.searchByName(username);

        assertNotNull(result);
        assertEquals(userEntity.getUsername(), result.getUsername());
        verify(userRepository, times(1)).findByUsername(username);
    }
}