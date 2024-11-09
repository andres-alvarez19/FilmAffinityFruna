package ufro.dci.filmaffinityfruna.service;

import org.springframework.stereotype.Service;
import ufro.dci.filmaffinityfruna.model.entity.MovieEntity;
import ufro.dci.filmaffinityfruna.repository.MovieRepository;

import java.util.List;

@Service
public class MovieService extends BaseService<MovieEntity, Long> {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        super(movieRepository);
        this.movieRepository = movieRepository;
    }

    @Override
    public boolean existsByUniqueProperty(MovieEntity movieEntity) {
        return movieRepository.existsByName(movieEntity.getName());
    }

    @Override
    public void register(MovieEntity movieEntity) {
        if (existsByUniqueProperty(movieEntity)) {
            throw new IllegalArgumentException("La película ya está registrada");
        }
        super.register(movieEntity);
    }

    public List<MovieEntity> searchByName(String name) {
        List<MovieEntity> movies = movieRepository.findByName(name);
        if (movies.isEmpty()) {
            throw new IllegalArgumentException("Película no encontrada");
        }
        return movies;
    }
}
