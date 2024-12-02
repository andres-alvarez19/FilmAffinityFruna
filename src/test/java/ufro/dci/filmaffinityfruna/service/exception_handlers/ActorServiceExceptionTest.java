package ufro.dci.filmaffinityfruna.service.exception_handlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ufro.dci.filmaffinityfruna.model.entity.ActorEntity;
import ufro.dci.filmaffinityfruna.repository.ActorRepository;
import ufro.dci.filmaffinityfruna.service.ActorService;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActorServiceExceptionTest {

    @Mock
    private ActorRepository actorRepository;

    @InjectMocks
    private ActorService actorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarActor_existenteLanzaExcepcion() {
        ActorEntity actor = new ActorEntity();
        actor.setName("Juan Pérez");
        actor.setDateOfBirth(LocalDate.of(1980, 1, 1));

        when(actorRepository.existsByNameAndDateOfBirth(actor.getName(), actor.getDateOfBirth())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> actorService.register(actor));
        verify(actorRepository, never()).save(any(ActorEntity.class));
    }

    @Test
    void actualizarActor_noExistenteLanzaExcepcion() {
        long id = 1L;
        ActorEntity actorActualizado = new ActorEntity();

        when(actorRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> actorService.update(id, actorActualizado));
        verify(actorRepository, never()).save(any(ActorEntity.class));
    }

    @Test
    void eliminarActorPorId_noExistenteLanzaExcepcion() {
        long id = 1L;

        when(actorRepository.existsById(id)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> actorService.deleteActorById(id));
        verify(actorRepository, never()).deleteById(id);
    }

    @Test
    void buscarActorPorNombre_noExistenteLanzaExcepcion() {
        String nombre = "Juan Pérez";

        when(actorRepository.existsByName(nombre)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> actorService.searchByName(nombre));
        verify(actorRepository, never()).findByName(nombre);
    }
}