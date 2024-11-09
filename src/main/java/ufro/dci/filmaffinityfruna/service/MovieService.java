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
    public void register(MovieEntity movieEntity) {
        if (movieRepository.existsByName(movieEntity.getName())) {
            throw new IllegalArgumentException("La película ya está registrada");
        }
        super.register(movieEntity);
    }

    @Override
    public void update(Long id, MovieEntity updatedMovie) {
        if (!movieRepository.existsById(id)) {
            throw new IllegalArgumentException("Película no encontrada");
        }
        super.update(id, updatedMovie);
    }

    @Override
    public void deleteById(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new IllegalArgumentException("Película no encontrada");
        }
        super.deleteById(id);
    }

    public List<MovieEntity> searchByName(String name) {
        if (!movieRepository.existsByName(name)) {
            throw new IllegalArgumentException("Película no encontrada");
        }
        return movieRepository.findByName(name);
    }
}
