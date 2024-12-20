package ufro.dci.filmaffinityfruna.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ufro.dci.filmaffinityfruna.model.entity.ActorEntity;
import ufro.dci.filmaffinityfruna.repository.ActorRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActorServiceTest {

    @Mock
    private ActorRepository actorRepository;

    @InjectMocks
    private ActorService actorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarActor_noExistenteGuardaActor() {
        ActorEntity actor = new ActorEntity();
        actor.setName("Juan Pérez");
        actor.setDateOfBirth(LocalDate.of(1980, 1, 1));

        when(actorRepository.existsByNameAndDateOfBirth(actor.getName(), actor.getDateOfBirth())).thenReturn(false);

        actorService.register(actor);
        verify(actorRepository, times(1)).save(actor);
    }

    @Test
    void actualizarActor_existenteActualizaDatos() {
        long id = 1L;
        ActorEntity actorExistente = new ActorEntity();
        actorExistente.setId(id);
        actorExistente.setName("Juan Pérez");
        ActorEntity actorActualizado = new ActorEntity();
        actorActualizado.setName("Juan Actualizado");
        actorActualizado.setNationality("Chileno");

        when(actorRepository.findById(id)).thenReturn(Optional.of(actorExistente));

        actorService.update(id, actorActualizado);

        verify(actorRepository, times(1)).save(actorExistente);
        assertEquals("Juan Actualizado", actorExistente.getName());
        assertEquals("Chileno", actorExistente.getNationality());
    }

    @Test
    void eliminarActorPorId_existenteEliminaActor() {
        long id = 1L;

        when(actorRepository.existsById(id)).thenReturn(true);

        actorService.deleteActorById(id);
        verify(actorRepository, times(1)).deleteById(id);
    }

    @Test
    void buscarActorPorNombre_existenteRetornaLista() {
        String nombre = "Juan Pérez";
        List<ActorEntity> listaActores = List.of(new ActorEntity());

        when(actorRepository.existsByName(nombre)).thenReturn(true);
        when(actorRepository.findByName(nombre)).thenReturn(listaActores);

        List<ActorEntity> resultado = actorService.searchByName(nombre);
        assertEquals(listaActores, resultado);
    }
}