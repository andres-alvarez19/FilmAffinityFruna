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
    public void register(DirectorEntity directorEntity) {
        if (directorRepository.existsByName(directorEntity.getName())) {
            throw new IllegalArgumentException("El nombre del director ya está registrado");
        }
        super.register(directorEntity);
    }

    public List<DirectorEntity> searchByName(String name) {
        if (!directorRepository.existsByName(name)) {
            throw new IllegalArgumentException("Director no encontrado");
        }
        return directorRepository.findByName(name);
    }

    @Override
    public void update(Long id, DirectorEntity modifiedDirector) {
        if (directorRepository.existsByName(modifiedDirector.getName())) {
            throw new IllegalArgumentException("El nombre del director ya está registrado");
        }
        super.update(id, modifiedDirector);
    }

    @Override
    public void deleteById(Long id) {
        if (!directorRepository.existsById(id)) {
            throw new IllegalArgumentException("Director no encontrado");
        }
        super.deleteById(id);
    }
}
