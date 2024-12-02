package ufro.dci.filmaffinityfruna.service.exception_handlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ufro.dci.filmaffinityfruna.model.entity.GenreEntity;
import ufro.dci.filmaffinityfruna.repository.GenreRepository;
import ufro.dci.filmaffinityfruna.service.GenreService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GenreServiceExceptionTest {

    @InjectMocks
    private GenreService genreService;

    @Mock
    private GenreRepository genreRepository;

    private GenreEntity genreEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        genreEntity = new GenreEntity();
        genreEntity.setName("Género Test");
    }

    @Test
    void testRegistrarGenero_YaExiste() {
        when(genreRepository.existsByName(genreEntity.getName())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            genreService.register(genreEntity);
        });
        verify(genreRepository, never()).save(any(GenreEntity.class));
    }

    @Test
    void testActualizarGenero_NoEncontrado() {
        String nombre = "Género Inexistente";
        GenreEntity generoModificado = new GenreEntity();
        generoModificado.setName("Género Modificado");

        when(genreRepository.existsByName(nombre)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            genreService.update(nombre, generoModificado);
        });
        verify(genreRepository, never()).save(any(GenreEntity.class));
    }

    @Test
    void testEliminarGenero_NoEncontrado() {
        String nombre = "Género Inexistente";
        when(genreRepository.existsByName(nombre)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            genreService.deleteGenreByName(nombre);
        });
        verify(genreRepository, never()).deleteByName(nombre);
    }

    @Test
    void testBuscarPorNombre_NoEncontrado() {
        String nombre = "Género Inexistente";
        when(genreRepository.existsByName(nombre)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            genreService.searchByName(nombre);
        });
        verify(genreRepository, never()).findByName(nombre);
    }
}