package ufro.dci.filmaffinityfruna.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ufro.dci.filmaffinityfruna.model.entity.ActorEntity;
import ufro.dci.filmaffinityfruna.model.entity.MovieEntity;
import ufro.dci.filmaffinityfruna.model.entity.GenreEntity;
import ufro.dci.filmaffinityfruna.model.entity.CastEntity;
import ufro.dci.filmaffinityfruna.repository.MovieRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieServiceTest {

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
        movieEntity.setReleaseYear(LocalDate.ofEpochDay(2023));
        movieEntity.setDuration(LocalTime.of(2, 0)); // 2 horas

        GenreEntity genre = new GenreEntity();
        genre.setName("Drama");
        movieEntity.setGenre(genre);

        Set<CastEntity> castSet = new HashSet<>();
        CastEntity castMember = new CastEntity();
        castMember.setCharacterName("Actor Test");
        castSet.add(castMember);
        movieEntity.setCast(castSet);
    }

    @Test
    void testRegistrarPelicula_Exito() {
        when(movieRepository.existsByName(movieEntity.getName())).thenReturn(false);

        movieService.register(movieEntity);

        verify(movieRepository, times(1)).save(movieEntity);
    }

    @Test
    void testActualizarPelicula_Exito() {
        long movieId = 1L;
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movieEntity));

        MovieEntity peliculaModificada = new MovieEntity();
        peliculaModificada.setName("Película Modificada");
        peliculaModificada.setReleaseYear(LocalDate.of(2024, 1, 1));
        peliculaModificada.setDuration(LocalTime.of(2, 10));

        GenreEntity nuevoGenero = new GenreEntity();
        nuevoGenero.setName("Comedia");
        peliculaModificada.setGenre(nuevoGenero);

        Set<CastEntity> nuevoReparto = new HashSet<>();
        ActorEntity nuevoActor = new ActorEntity();
        nuevoActor.setName("Actor Modificado");
        CastEntity nuevoMiembro = new CastEntity();
        nuevoMiembro.setActor(nuevoActor);
        nuevoMiembro.setCharacterName("Nombre del Personaje Modificado");
        nuevoReparto.add(nuevoMiembro);
        peliculaModificada.setCast(nuevoReparto);

        movieService.update(movieId, peliculaModificada);

        assertEquals("Película Modificada", movieEntity.getName());
        assertEquals(2024, movieEntity.getReleaseYear().getYear());
        assertEquals(LocalTime.of(2, 10), movieEntity.getDuration());
        assertEquals("Comedia", movieEntity.getGenre().getName());
        assertEquals(1, movieEntity.getCast().size());
        assertEquals("Actor Modificado", movieEntity.getCast().iterator().next().getActor().getName());
        verify(movieRepository, times(1)).save(movieEntity);
    }

    @Test
    void testEliminarPelicula_Exito() {
        long movieId = 1L;
        when(movieRepository.existsById(movieId)).thenReturn(true);

        movieService.deleteMovieById(movieId);

        verify(movieRepository, times(1)).deleteById(movieId);
    }

    @Test
    void testBuscarPorNombre_Exito() {
        String nombre = "Película Test";
        when(movieRepository.existsByName(nombre)).thenReturn(true);
        when(movieRepository.findByName(nombre)).thenReturn(List.of(movieEntity));

        List<MovieEntity> resultado = movieService.searchByName(nombre);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(movieEntity.getName(), resultado.get(0).getName());
    }
}