package ufro.dci.filmaffinityfruna.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ufro.dci.filmaffinityfruna.model.dto.MovieDTO;
import ufro.dci.filmaffinityfruna.model.entity.MovieEntity;
import ufro.dci.filmaffinityfruna.repository.MovieRepository;

import java.util.ArrayList;
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

    public void update(long id, MovieEntity updatedMovie) {
        Optional<MovieEntity> existingMovie = movieRepository.findById(id);
        if (existingMovie.isPresent()) {
            MovieEntity movie = existingMovie.get();
            movie.setName(updatedMovie.getName());
            movie.setSynopsis(updatedMovie.getSynopsis());
            movie.setCountry(updatedMovie.getCountry());
            movie.setReleaseYear(updatedMovie.getReleaseYear());
            movie.setDuration(updatedMovie.getDuration());
            movie.setGenre(updatedMovie.getGenre());
            movie.setCast(updatedMovie.getCast());
            movie.setDirector(updatedMovie.getDirector());
            movie.setPhotoUrl(updatedMovie.getPhotoUrl());
            movie.setTrailerUrl(updatedMovie.getTrailerUrl());
            movie.setOverviewUrl(updatedMovie.getOverviewUrl());
            movieRepository.save(movie);
        } else {
            throw new IllegalArgumentException("Película no encontrada");
        }
    }

    public void deleteMovieById(long id) {
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

    public List<MovieDTO> getAllMovies() {
        List<MovieDTO> movies = new ArrayList<>();
        movieRepository.findAll().forEach(movieEntity -> movies.add(new MovieDTO(movieEntity)));
        return movies;
    }

    public MovieDTO findMovieById(Long id) {
        Optional<MovieEntity> movieEntity = movieRepository.findById(id);
        if (movieEntity.isPresent()) {
            return new MovieDTO(movieEntity.get());
        } else {
            throw new IllegalArgumentException("Película no encontrada");
        }
    }

    public List<MovieDTO> getBestMovies() {
        List<MovieDTO> movies = new ArrayList<>();
        movieRepository.findTop10ByOrderByRatingDesc().forEach(movieEntity -> movies.add(new MovieDTO(movieEntity)));
        return movies;
    }
}
