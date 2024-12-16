package ufro.dci.filmaffinityfruna.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ufro.dci.filmaffinityfruna.model.entity.GenreEntity;
import ufro.dci.filmaffinityfruna.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public void register(GenreEntity genreEntity) {
        if (genreRepository.existsByName(genreEntity.getName())) {
            throw new IllegalArgumentException("El género ya está registrado");
        } else {
            genreRepository.save(genreEntity);
        }
    }

    public void update(String name, GenreEntity updatedGenre) {
        if (genreRepository.existsByName(name)) {
            GenreEntity genre = genreRepository.findByName(name);
            genre.setName(updatedGenre.getName());
            genreRepository.save(genre);
        } else {
            throw new IllegalArgumentException("Género no encontrado");
        }
    }

    public void deleteGenreByName(String name) {
        if (!genreRepository.existsByName(name)) {
            throw new IllegalArgumentException("Género no encontrado");
        } else {
            genreRepository.deleteByName(name);
        }
    }

    public GenreEntity searchByName(String name) {
        if (!genreRepository.existsByName(name)) {
            throw new IllegalArgumentException("Género no encontrado");
        } else {
            return genreRepository.findByName(name);
        }
    }

    public List<GenreEntity> getAllGenres() {
        List<GenreEntity> genres = new ArrayList<>();
        genreRepository.findAll().forEach(genres::add);
        return genres;
    }
}
