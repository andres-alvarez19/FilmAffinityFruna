package ufro.dci.filmaffinityfruna.model.dto;

import ufro.dci.filmaffinityfruna.model.entity.ActorEntity;

import java.time.LocalDate;

public record ActorDTO(long id, String name, String nationality, String dateOfBirth, String dateOfDeath, String wikipediaLink, String photoUrl, String biography) {

    public ActorDTO(ActorEntity actorEntity) {
        this(actorEntity.getId(), actorEntity.getName(), actorEntity.getNationality(),
             actorEntity.getDateOfBirth().toString(),
             actorEntity.getDateOfDeath() != null ? actorEntity.getDateOfDeath().toString() : null,
             actorEntity.getWikipediaLink(), actorEntity.getPhotoUrl(), actorEntity.getBiography());
    }

    public ActorEntity toEntity() {
        ActorEntity actorEntity = new ActorEntity();
        actorEntity.setId(this.id);
        actorEntity.setName(this.name);
        actorEntity.setNationality(this.nationality);
        actorEntity.setDateOfBirth(LocalDate.parse(this.dateOfBirth));
        actorEntity.setDateOfDeath(this.dateOfDeath != null ? LocalDate.parse(this.dateOfDeath) : null);
        actorEntity.setWikipediaLink(this.wikipediaLink);
        actorEntity.setPhotoUrl(this.photoUrl);
        actorEntity.setBiography(this.biography);
        return actorEntity;
    }
}