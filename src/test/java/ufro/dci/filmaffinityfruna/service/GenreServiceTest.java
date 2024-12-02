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
    void testEliminarGenero_Exito() {
        String nombre = "Género Test";
        when(genreRepository.existsByName(nombre)).thenReturn(true);

        genreService.deleteGenreByName(nombre);

        verify(genreRepository, times(1)).deleteByName(nombre);
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

}

