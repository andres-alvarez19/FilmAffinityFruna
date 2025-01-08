package ufro.dci.filmaffinityfruna.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ufro.dci.filmaffinityfruna.model.dto.GenreDTO;
import ufro.dci.filmaffinityfruna.model.entity.GenreEntity;
import ufro.dci.filmaffinityfruna.repository.GenreRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GenreServiceTest {

    @InjectMocks
    private GenreService genreService;

    @Mock
    private GenreRepository genreRepository;

    private GenreDTO genreDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        genreDTO = new GenreDTO("Género Test", "Descripción Test");
    }

    @Test
    void testRegistrarGenero_Exito() {
        when(genreRepository.existsByName(genreDTO.name())).thenReturn(false);

        genreService.register(genreDTO.toEntity());

        verify(genreRepository, times(1)).save(any(GenreEntity.class));
    }

    @Test
    void testActualizarGenero_Exito() {
        String nombre = "Género Test";
        GenreDTO generoModificado = new GenreDTO("Género Modificado", "Descripción Modificada");

        when(genreRepository.existsByName(nombre)).thenReturn(true);
        when(genreRepository.findByName(nombre)).thenReturn(Optional.of(genreDTO));

        genreService.update(nombre, generoModificado.toEntity());

        assertEquals("Género Modificado", genreDTO.name());
        verify(genreRepository, times(1)).save(any(GenreEntity.class));
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
        when(genreRepository.findByName(nombre)).thenReturn(Optional.of(genreDTO));

        GenreDTO resultado = genreService.searchByName(nombre);

        assertNotNull(resultado);
        assertEquals(genreDTO.name(), resultado.name());
    }
}