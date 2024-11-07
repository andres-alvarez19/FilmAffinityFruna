package ufro.dci.filmaffinityfruna.service.exception_handlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ufro.dci.filmaffinityfruna.model.entity.MovieEntity;
import ufro.dci.filmaffinityfruna.repository.MovieRepository;
import ufro.dci.filmaffinityfruna.service.MovieService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieServiceExceptionTest {

    @InjectMocks
    private MovieService movieService;

    @Mock
    private MovieRepository movieRepository;

    private MovieEntity movieEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        movieEntity = new MovieEntity();
        movieEntity.setName("Película Test");
    }

    @Test
    void testRegistrarPelicula_YaExiste() {
        when(movieRepository.existsByName(movieEntity.getName())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            movieService.register(movieEntity);
        });
        verify(movieRepository, never()).save(any(MovieEntity.class));
    }

    @Test
    void testActualizarPelicula_NoEncontrada() {
        long movieId = 1L;
        when(movieRepository.findById(movieId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            movieService.update(movieId, movieEntity);
        });
        verify(movieRepository, never()).save(any(MovieEntity.class));
    }

    @Test
    void testEliminarPelicula_NoEncontrada() {
        long movieId = 1L;
        when(movieRepository.existsById(movieId)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            movieService.deleteMovieById(movieId);
        });
        verify(movieRepository, never()).deleteById(movieId);
    }

    @Test
    void testBuscarPorNombre_NoEncontrada() {
        String nombre = "Película Inexistente";
        when(movieRepository.existsByName(nombre)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            movieService.searchByName(nombre);
        });
        verify(movieRepository, never()).findByName(nombre);
    }
}