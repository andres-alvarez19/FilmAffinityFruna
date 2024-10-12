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

    public void register(MovieEntity movieEntity) {
        if (movieRepository.existsByName(movieEntity.getName())) {
            throw new IllegalArgumentException("La película ya está registrada");
        } else {
            movieRepository.save(movieEntity);
        }
    }

    public void update(Long id, MovieEntity updatedMovie) {
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
    }

    public void deleteMovieById(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new IllegalArgumentException("Película no encontrada");
        } else {
            movieRepository.deleteById(id);
        }
    }

    public List<MovieEntity> searchByName(String name) {
        if (!movieRepository.existsByName(name)) {
            throw new IllegalArgumentException("Película no encontrada");
        } else {
            return movieRepository.findByName(name);
        }
    }
}
