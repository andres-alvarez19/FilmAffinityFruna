package ufro.dci.filmaffinityfruna.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ufro.dci.filmaffinityfruna.model.entity.MovieEntity;
import ufro.dci.filmaffinityfruna.repository.MovieRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final ValidationService validationService; // Inyectamos el ValidationService

    public void register(MovieEntity movieEntity) {
        // Delegamos la validación al ValidationService
        if (validationService.existsMovie(movieEntity.getName())) {
            throw new IllegalArgumentException("La película ya está registrada");
        } else {
            movieRepository.save(movieEntity);
        }
    }

    public void update(Long id, MovieEntity updatedMovie) {
        // Validamos si la película existe antes de actualizarla
        if (validationService.existsMovie(updatedMovie.getName())) {
            Optional<MovieEntity> existingMovie = movieRepository.findById(id);
            if (existingMovie.isPresent()) {
                MovieEntity movie = existingMovie.get();
                movie.setName(updatedMovie.getName());
                movie.setReleaseYear(updatedMovie.getReleaseYear());
                movie.setDuration(updatedMovie.getDuration());
                movie.setGenre(updatedMovie.getGenre());
                movie.setCast(updatedMovie.getCast());
                movieRepository.save(movie);
            } else {
                throw new IllegalArgumentException("Película no encontrada");
            }
        } else {
            throw new IllegalArgumentException("La película ya está registrada");
        }
    }

    public void deleteMovieById(Long id) {
        // Validamos si la película existe antes de eliminarla
        if (!movieRepository.existsById(id)) {
            throw new IllegalArgumentException("Película no encontrada");
        } else {
            movieRepository.deleteById(id);
        }
    }

    public List<MovieEntity> searchByName(String name) {
        // Validamos si la película existe antes de buscarla
        if (!movieRepository.existsByName(name)) {
            throw new IllegalArgumentException("Película no encontrada");
        } else {
            return movieRepository.findByName(name);
        }
    }
}
