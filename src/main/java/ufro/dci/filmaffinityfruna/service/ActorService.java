package ufro.dci.filmaffinityfruna.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ufro.dci.filmaffinityfruna.model.entity.ActorEntity;
import ufro.dci.filmaffinityfruna.repository.ActorRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ActorService {

    private final ActorRepository actorRepository;

    public void register(ActorEntity actorEntity) {
        if (actorRepository.existsByNameAndDateOfBirth(actorEntity.getName(), actorEntity.getDateOfBirth())) {
            throw new IllegalArgumentException("El actor ya está registrado");
        } else {
            actorRepository.save(actorEntity);
        }
    }

    public void update(Long id, ActorEntity updatedActor) {
        Optional<ActorEntity> existingActor = actorRepository.findById(id);
        if (existingActor.isPresent()) {
            ActorEntity actor = existingActor.get();
            actor.setName(updatedActor.getName());
            actor.setNationality(updatedActor.getNationality());
            actor.setDateOfBirth(updatedActor.getDateOfBirth());
            actor.setWikipediaLink(updatedActor.getWikipediaLink());
            actor.setDateOfDeath(updatedActor.getDateOfDeath());
            actorRepository.save(actor);
        } else {
            throw new IllegalArgumentException("Actor no encontrado");
        }
    }

    public void deleteActorById(Long id) {
        if (!actorRepository.existsById(id)) {
            throw new IllegalArgumentException("Actor no encontrado");
        } else {
            actorRepository.deleteById(id);
        }
    }

    public List<ActorEntity> searchByName(String name) {
        if (!actorRepository.existsByName(name)) {
            throw new IllegalArgumentException("Actor no encontrado");
        } else {
            return actorRepository.findByName(name);
        }
    }

}
