package ufro.dci.filmaffinityfruna.service;

import org.springframework.stereotype.Service;
import ufro.dci.filmaffinityfruna.model.entity.GenreEntity;
import ufro.dci.filmaffinityfruna.repository.GenreRepository;

@Service
public class GenreService extends BaseService<GenreEntity, Long> {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        super(genreRepository);
        this.genreRepository = genreRepository;
    }

    @Override
    public boolean existsByUniqueProperty(GenreEntity genreEntity) {
        return genreRepository.existsByName(genreEntity.getName());
    }

    @Override
    public void register(GenreEntity genreEntity) {
        if (existsByUniqueProperty(genreEntity)) {
            throw new IllegalArgumentException("El género ya está registrado");
        }
        super.register(genreEntity);
    }

    public GenreEntity searchByName(String name) {
        return genreRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Género no encontrado"));
    }

    public void deleteByName(String name) {
        if (!genreRepository.existsByName(name)) {
            throw new IllegalArgumentException("Género no encontrado");
        }
        genreRepository.deleteByName(name);
    }
}
