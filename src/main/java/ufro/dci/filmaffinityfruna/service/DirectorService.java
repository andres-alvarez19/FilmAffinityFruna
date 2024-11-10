package ufro.dci.filmaffinityfruna.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import ufro.dci.filmaffinityfruna.model.entity.DirectorEntity;
import ufro.dci.filmaffinityfruna.repository.DirectorRepository;

import java.util.List;

public class DirectorServiceTest {

    @Mock
    private DirectorRepository directorRepository;

    private DirectorService directorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        directorService = new DirectorService(directorRepository);
    }

    @Test
    void testRegisterDirector() {
        DirectorEntity directorEntity = new DirectorEntity();
        directorEntity.setName("Test Director");

        when(directorRepository.existsByName(anyString())).thenReturn(false);

        directorService.register(directorEntity);

        verify(directorRepository, times(1)).save(directorEntity);
    }

    @Test
    void testRegisterDirectorAlreadyExists() {
        DirectorEntity directorEntity = new DirectorEntity();
        directorEntity.setName("Test Director");

        when(directorRepository.existsByName(anyString())).thenReturn(true);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            directorService.register(directorEntity);
        });

        assertEquals("El director ya está registrado", thrown.getMessage());
    }

    @Test
    void testSearchByName() {
        DirectorEntity directorEntity = new DirectorEntity();
        directorEntity.setName("Test Director");

        when(directorRepository.findByName("Test Director")).thenReturn(List.of(directorEntity));

        List<DirectorEntity> result = directorService.searchByName("Test Director");

        assertFalse(result.isEmpty());
        assertEquals("Test Director", result.get(0).getName());
    }

    @Test
    void testSearchByNameNotFound() {
        when(directorRepository.findByName("Nonexistent Director")).thenReturn(List.of());

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            directorService.searchByName("Nonexistent Director");
        });

        assertEquals("Director no encontrado", thrown.getMessage());
    }
}
