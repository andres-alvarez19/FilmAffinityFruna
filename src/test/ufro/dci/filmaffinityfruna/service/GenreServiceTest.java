package ufro.dci.filmaffinityfruna.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ufro.dci.filmaffinityfruna.model.entity.GenreEntity;
import ufro.dci.filmaffinityfruna.repository.GenreRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GenreServiceTest {

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
    void testRegistrarGenero_Exito() {
        when(genreRepository.existsByName(genreEntity.getName())).thenReturn(false);

        genreService.register(genreEntity);

        verify(genreRepository, times(1)).save(genreEntity);
    }

    @Test
    void testRegistrarGenero_YaExiste() {
        when(genreRepository.existsByName(genreEntity.getName())).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            genreService.register(genreEntity);
        });

        assertEquals("El género ya está registrado", exception.getMessage());
    }

    @Test
    void testActualizarGenero_Exito() {
        String nombre = "Género Test";
        GenreEntity generoModificado = new GenreEntity();
        generoModificado.setName("Género Modificado");

        when(genreRepository.existsByName(nombre)).thenReturn(true);
        when(genreRepository.findByName(nombre)).thenReturn(genreEntity);

        genreService.update(nombre, generoModificado);

        assertEquals("Género Modificado", genreEntity.getName());
        verify(genreRepository, times(1)).save(genreEntity);
    }

    @Test
    void testActualizarGenero_NoEncontrado() {
        String nombre = "Género Inexistente";
        GenreEntity generoModificado = new GenreEntity();
        generoModificado.setName("Género Modificado");

        when(genreRepository.existsByName(nombre)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            genreService.update(nombre, generoModificado);
        });

        assertEquals("Género no encontrado", exception.getMessage());
    }

    @Test
    void testEliminarGenero_Exito() {
        String nombre = "Género Test";
        when(genreRepository.existsByName(nombre)).thenReturn(true);

        genreService.deleteGenreByName(nombre);

        verify(genreRepository, times(1)).deleteByName(nombre);
    }

    @Test
    void testEliminarGenero_NoEncontrado() {
        String nombre = "Género Inexistente";
        when(genreRepository.existsByName(nombre)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            genreService.deleteGenreByName(nombre);
        });

        assertEquals("Género no encontrado", exception.getMessage());
    }

    @Test
    void testBuscarPorNombre_Exito() {
        String nombre = "Género Test";
        when(genreRepository.existsByName(nombre)).thenReturn(true);
        when(genreRepository.findByName(nombre)).thenReturn(genreEntity);

        GenreEntity resultado = genreService.searchByName(nombre);

        assertNotNull(resultado);
        assertEquals(genreEntity.getName(), resultado.getName());
    }

    @Test
    void testBuscarPorNombre_NoEncontrado() {
        String nombre = "Género Inexistente";
        when(genreRepository.existsByName(nombre)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            genreService.searchByName(nombre);
        });

        assertEquals("Género no encontrado", exception.getMessage());
    }
}

