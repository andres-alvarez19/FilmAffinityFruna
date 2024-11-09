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

    @Override
    public boolean existsByUniqueProperty(ActorEntity actorEntity) {
        return actorRepository.existsByNameAndDateOfBirth(actorEntity.getName(), actorEntity.getDateOfBirth());
    }

    @Override
    public void register(ActorEntity actorEntity) {
        if (existsByUniqueProperty(actorEntity)) {
            throw new IllegalArgumentException("El actor ya está registrado");
        }
        super.register(actorEntity);
    }

    public List<ActorEntity> searchByName(String name) {
        List<ActorEntity> actors = actorRepository.findByName(name);
        if (actors.isEmpty()) {
            throw new IllegalArgumentException("Actor no encontrado");
        }
        return actors;
    }
}
