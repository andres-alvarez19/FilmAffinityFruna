package ufro.dci.filmaffinityfruna.model.dto;

import ufro.dci.filmaffinityfruna.model.entity.ActorEntity;

import java.time.LocalDate;

public record ActorDTO(long id, String name, String nationality, String dateOfBirth, String dateOfDeath, String wikipediaLink, String photoUrl, String biography) {

    public ActorDTO(ActorEntity actorEntity) {
        this(actorEntity.getId(), actorEntity.getName(), actorEntity.getNationality(),
             actorEntity.getDateOfBirth().toString(),
             actorEntity.getDateOfDeath() != null ? actorEntity.getDateOfDeath().toString() : null,
             actorEntity.getWikipediaLink(), actorEntity.getPhotoUrl());
    }
}