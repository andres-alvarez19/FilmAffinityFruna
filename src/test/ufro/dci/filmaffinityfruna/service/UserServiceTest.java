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
        userEntity.setId(1);
        userEntity.setUsername("usuario");
        userEntity.setEmail("usuario@ejemplo.com");
    }

    @Test
    void testRegistrarUsuario_Exito() {
        when(userRepository.existsByEmail(userEntity.getEmail())).thenReturn(false);

        userService.register(userEntity);

        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void testRegistrarUsuario_EmailYaRegistrado() {
        when(userRepository.existsByEmail(userEntity.getEmail())).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.register(userEntity);
        });

        assertEquals("El email ya está registrado", exception.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void testActualizarUsuario_Exito() {
        Long userId = 1L;
        UserEntity modifiedUser = new UserEntity();
        modifiedUser.setUsername("usuario_modificado");
        modifiedUser.setEmail("nuevo@ejemplo.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userRepository.existsByEmail(modifiedUser.getEmail())).thenReturn(false);

        userService.update(userId, modifiedUser);

        assertEquals("usuario_modificado", userEntity.getUsername());
        assertEquals("nuevo@ejemplo.com", userEntity.getEmail());
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void testActualizarUsuario_EmailYaRegistrado() {
        Long userId = 1L;
        UserEntity modifiedUser = new UserEntity();
        modifiedUser.setUsername("usuario_modificado");
        modifiedUser.setEmail("usuario@ejemplo.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userRepository.existsByEmail(modifiedUser.getEmail())).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.update(userId, modifiedUser);
        });

        assertEquals("El email ya está registrado", exception.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void testActualizarUsuario_UsuarioNoEncontrado() {
        Long userId = 1L;
        UserEntity modifiedUser = new UserEntity();
        modifiedUser.setUsername("usuario_modificado");
        modifiedUser.setEmail("nuevo@ejemplo.com");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.update(userId, modifiedUser);
        });

        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void testEliminarUsuarioPorId_Exito() {
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);

        userService.deleteUserById(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testEliminarUsuarioPorId_UsuarioNoEncontrado() {
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.deleteUserById(userId);
        });

        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(userRepository, never()).deleteById(any());
    }

    @Test
    void testBuscarPorNombre_Exito() {
        String username = "usuario";
        when(userRepository.existsByUsername(username)).thenReturn(true);
        when(userRepository.findByUsername(username)).thenReturn(userEntity);

        UserEntity foundUser = userService.searchByName(username);

        assertEquals(userEntity, foundUser);
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void testBuscarPorNombre_UsuarioNoEncontrado() {
        String username = "usuario_no_existente";
        when(userRepository.existsByUsername(username)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.searchByName(username);
        });

        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(userRepository, never()).findByUsername(any());
    }

    @Test
    void testBuscarPorId_Exito() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        UserEntity foundUser = userService.findById(userId);

        assertEquals(userEntity, foundUser);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testBuscarPorId_UsuarioNoEncontrado() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.findById(userId);
        });

        assertEquals("Usuario no encontrado", exception.getMessage());
    }
}
