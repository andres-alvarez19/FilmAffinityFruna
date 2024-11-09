package ufro.dci.filmaffinityfruna.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ufro.dci.filmaffinityfruna.model.entity.GenreEntity;
import ufro.dci.filmaffinityfruna.repository.GenreRepository;

@RequiredArgsConstructor
@Service
public class GenreService {

    private final GenreRepository genreRepository;
    private final ValidationService validationService; // Inyectamos el ValidationService

    public void register(GenreEntity genreEntity) {
        // Delegamos la validación al ValidationService
        if (validationService.existsGenre(genreEntity.getName())) {
            throw new IllegalArgumentException("El género ya está registrado");
        } else {
            genreRepository.save(genreEntity);
        }
    }

    public void update(String name, GenreEntity updatedGenre) {
        // Validamos si el género existe antes de actualizarlo
        if (validationService.existsGenre(name)) {
            GenreEntity genre = genreRepository.findByName(name);
            genre.setName(updatedGenre.getName());
            genreRepository.save(genre);
        } else {
            throw new IllegalArgumentException("Género no encontrado");
        }
    }

    public void deleteGenreByName(String name) {
        // Validamos si el género existe antes de eliminarlo
        if (!genreRepository.existsByName(name)) {
            throw new IllegalArgumentException("Género no encontrado");
        } else {
            genreRepository.deleteByName(name);
        }
    }

    public GenreEntity searchByName(String name) {
        // Validamos si el género existe antes de devolverlo
        if (!genreRepository.existsByName(name)) {
            throw new IllegalArgumentException("Género no encontrado");
        } else {
            return genreRepository.findByName(name);
        }
    }

}
