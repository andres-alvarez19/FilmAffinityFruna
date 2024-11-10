package ufro.dci.filmaffinityfruna.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import ufro.dci.filmaffinityfruna.model.entity.ActorEntity;
import ufro.dci.filmaffinityfruna.repository.ActorRepository;

import java.util.Optional;

public class ActorServiceTest {

    @Mock
    private ActorRepository actorRepository;

    private ActorService actorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        actorService = new ActorService(actorRepository);
    }

    @Test
    void testRegisterActor() {
        ActorEntity actorEntity = new ActorEntity();
        actorEntity.setName("Test Actor");
        actorEntity.setDateOfBirth("1980-01-01");

        // Simula que no existe el actor
        when(actorRepository.existsByNameAndDateOfBirth(anyString(), anyString())).thenReturn(false);

        actorService.register(actorEntity);

        // Verifica que el actor se ha guardado
        verify(actorRepository, times(1)).save(actorEntity);
    }

    @Test
    void testRegisterActorAlreadyExists() {
        ActorEntity actorEntity = new ActorEntity();
        actorEntity.setName("Test Actor");
        actorEntity.setDateOfBirth("1980-01-01");

        // Simula que el actor ya existe
        when(actorRepository.existsByNameAndDateOfBirth(anyString(), anyString())).thenReturn(true);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            actorService.register(actorEntity);
        });

        assertEquals("El actor ya está registrado", thrown.getMessage());
    }

    @Test
    void testSearchByName() {
        ActorEntity actorEntity = new ActorEntity();
        actorEntity.setName("Test Actor");

        when(actorRepository.findByName("Test Actor")).thenReturn(List.of(actorEntity));

        List<ActorEntity> result = actorService.searchByName("Test Actor");

        assertFalse(result.isEmpty());
        assertEquals("Test Actor", result.get(0).getName());
    }

    @Test
    void testSearchByNameNotFound() {
        when(actorRepository.findByName("Nonexistent Actor")).thenReturn(List.of());

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            actorService.searchByName("Nonexistent Actor");
        });

        assertEquals("Actor no encontrado", thrown.getMessage());
    }
}
