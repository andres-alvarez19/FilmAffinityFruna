package ufro.dci.filmaffinityfruna.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ufro.dci.filmaffinityfruna.model.dto.ActorDTO;
import ufro.dci.filmaffinityfruna.model.dto.MovieDTO;
import ufro.dci.filmaffinityfruna.model.entity.ActorEntity;
import ufro.dci.filmaffinityfruna.repository.ActorRepository;

import java.util.ArrayList;
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

    public void update(long id, ActorEntity updatedActor) {
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

    public void deleteActorById(long id) {
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

    public List<ActorDTO> searchByNameIgnoreCase(String name) {
        List<ActorDTO> actors = new ArrayList<>();
        actorRepository.findByNameContainingIgnoreCase(name).forEach(actorEntity -> actors.add(new ActorDTO(actorEntity)));
        return actors;
    }

    public List<ActorDTO> listAll() {
        List<ActorDTO> actors = new ArrayList<>();
        actorRepository.findAll().forEach(actorEntity -> actors.add(new ActorDTO(actorEntity)));
        return actors;
    }

    public ActorDTO searchById(Long id) {
        Optional<ActorEntity> actor = actorRepository.findById(id);
        if (actor.isPresent()) {
            return new ActorDTO(actor.get());
        }else {
            throw new IllegalArgumentException("Actor no encontrado");
        }
    }

    public List<MovieDTO> getMoviesByActorId(Long actorId) {
        Optional<ActorEntity> actor = actorRepository.findById(actorId);
        if (actor.isPresent()) {
            return actor.get().getCharactersPlayed().stream()
                    .map(castEntity -> new MovieDTO(castEntity.getMovie()))
                    .toList();
        } else {
            throw new IllegalArgumentException("Actor no encontrado");
        }

    }
}
