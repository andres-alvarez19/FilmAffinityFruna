package ufro.dci.filmaffinityfruna.service;

import org.springframework.stereotype.Service;
import ufro.dci.filmaffinityfruna.model.entity.ActorEntity;
import ufro.dci.filmaffinityfruna.repository.ActorRepository;

import java.util.List;

@Service
public class ActorService extends BaseService<ActorEntity, Long> {

    private final ActorRepository actorRepository;

    public ActorService(ActorRepository actorRepository) {
        super(actorRepository);
        this.actorRepository = actorRepository;
    }

    // Register only if actor does not already exist
    @Override
    public void register(ActorEntity actorEntity) {
        if (actorRepository.existsByNameAndDateOfBirth(actorEntity.getName(), actorEntity.getDateOfBirth())) {
            throw new IllegalArgumentException("El actor ya está registrado");
        }
        super.register(actorEntity);
    }

    // Search actor by name with validation
    public List<ActorEntity> searchByName(String name) {
        if (!actorRepository.existsByName(name)) {
            throw new IllegalArgumentException("Actor no encontrado");
        }
        return actorRepository.findByName(name);
    }
}
