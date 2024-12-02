package ufro.dci.filmaffinityfruna.service.exception_handlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ufro.dci.filmaffinityfruna.model.entity.DirectorEntity;
import ufro.dci.filmaffinityfruna.repository.DirectorRepository;
import ufro.dci.filmaffinityfruna.service.DirectorService;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DirectorServiceExceptionTest {

    @InjectMocks
    private DirectorService directorService;

    @Mock
    private DirectorRepository directorRepository;

    private DirectorEntity directorEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        directorEntity = new DirectorEntity();
        directorEntity.setName("Director Test");
        directorEntity.setNationality("Testland");
        directorEntity.setDateOfBirth(LocalDate.parse("1980-01-01"));
        directorEntity.setWikipediaLink("http://test.com");
    }

 @Test
void testRegistrarDirector_YaExiste() {
    when(directorRepository.existsByName(directorEntity.getName())).thenReturn(true);

    assertThrows(IllegalArgumentException.class, () -> {
        directorService.register(directorEntity);
    });
    verify(directorRepository, never()).save(any(DirectorEntity.class));
}

@Test
void testActualizarDirector_NoEncontrado() {
    long directorId = 1L;
    when(directorRepository.findById(directorId)).thenReturn(Optional.empty());

    assertThrows(IllegalArgumentException.class, () -> {
        directorService.update(directorId, directorEntity);
    });
    verify(directorRepository, never()).save(any(DirectorEntity.class));
}

@Test
void testEliminarDirector_NoEncontrado() {
    long directorId = 1L;
    when(directorRepository.existsById(directorId)).thenReturn(false);

    assertThrows(IllegalArgumentException.class, () -> {
        directorService.deleteDirectorById(directorId);
    });
    verify(directorRepository, never()).deleteById(directorId);
}

@Test
void testBuscarPorNombre_NoEncontrado() {
    String nombre = "Director Inexistente";
    when(directorRepository.existsByName(nombre)).thenReturn(false);

    assertThrows(IllegalArgumentException.class, () -> {
        directorService.searchByName(nombre);
    });
    verify(directorRepository, never()).findByName(nombre);
}
}