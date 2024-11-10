package ufro.dci.filmaffinityfruna.service;

import org.springframework.stereotype.Service;
import ufro.dci.filmaffinityfruna.model.entity.DirectorEntity;
import ufro.dci.filmaffinityfruna.repository.DirectorRepository;

import java.util.List;

@Service
public class DirectorService extends BaseService<DirectorEntity, Long> {

    private final DirectorRepository directorRepository;

    public DirectorService(DirectorRepository directorRepository) {
        super(directorRepository);
        this.directorRepository = directorRepository;
    }

    @Override
    public boolean existsByUniqueProperty(DirectorEntity directorEntity) {
        return directorRepository.existsByName(directorEntity.getName());
    }

    @Override
    public void register(DirectorEntity directorEntity) {
        if (existsByUniqueProperty(directorEntity)) {
            throw new IllegalArgumentException("El director ya está registrado");
        }
        super.register(directorEntity);
    }

    public List<DirectorEntity> searchByName(String name) {
        List<DirectorEntity> directors = directorRepository.findByName(name);
        if (directors.isEmpty()) {
            throw new IllegalArgumentException("Director no encontrado");
        }
        return directors;
    }
}
