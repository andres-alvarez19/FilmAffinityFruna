package ufro.dci.filmaffinityfruna.service.exception_handlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ufro.dci.filmaffinityfruna.model.entity.UserEntity;
import ufro.dci.filmaffinityfruna.repository.UserRepository;
import ufro.dci.filmaffinityfruna.service.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceExceptionTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userEntity = new UserEntity();
        userEntity.setUsername("testuser");
        userEntity.setPassword("password");
        userEntity.setEmail("testuser@example.com");
    }

    @Test
    void testRegisterUser_AlreadyExists() {
        when(userRepository.existsByEmail(userEntity.getEmail())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            userService.register(userEntity);
        });
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void testUpdateUser_NotFound() {
        long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            userService.update(userId, userEntity);
        });
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void testDeleteUser_NotFound() {
        long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            userService.deleteUserById(userId);
        });
        verify(userRepository, never()).deleteById(userId);
    }

    @Test
    void testSearchByUsername_NotFound() {
        String username = "nonexistentuser";
        when(userRepository.existsByUsername(username)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            userService.searchByName(username);
        });
        verify(userRepository, never()).findByUsername(username);
    }
}