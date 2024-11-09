package ufro.dci.filmaffinityfruna.service;

import org.springframework.stereotype.Service;
import ufro.dci.filmaffinityfruna.model.entity.GenreEntity;
import ufro.dci.filmaffinityfruna.repository.GenreRepository;

@Service
public class GenreService extends BaseService<GenreEntity, String> {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        super(genreRepository);
        this.genreRepository = genreRepository;
    }

    @Override
    public void register(GenreEntity genreEntity) {
        if (genreRepository.existsByName(genreEntity.getName())) {
            throw new IllegalArgumentException("El género ya está registrado");
        }
        super.register(genreEntity);
    }

    @Override
    public void update(String name, GenreEntity updatedGenre) {
        if (genreRepository.existsByName(name)) {
            GenreEntity genre = genreRepository.findByName(name);
            genre.setName(updatedGenre.getName());
            genreRepository.save(genre);
        } else {
            throw new IllegalArgumentException("Género no encontrado");
        }
    }

    public GenreEntity searchByName(String name) {
        if (!genreRepository.existsByName(name)) {
            throw new IllegalArgumentException("Género no encontrado");
        }
        return genreRepository.findByName(name);
    }

    @Override
    public void deleteById(String name) {
        if (!genreRepository.existsByName(name)) {
            throw new IllegalArgumentException("Género no encontrado");
        }
        super.deleteById(name);
    }
}
