package ufro.dci.filmaffinityfruna.model.dto;

import ufro.dci.filmaffinityfruna.model.entity.DirectorEntity;

import java.time.LocalDate;

public record DirectorDTO(long id, String name, String nationality, String dateOfBirth, String dateOfDeath, String wikipediaLink, String photoUrl) {

    public DirectorDTO(DirectorEntity directorEntity) {
        this(directorEntity.getId(), directorEntity.getName(), directorEntity.getNationality(),
             directorEntity.getDateOfBirth().toString(),
             directorEntity.getDateOfDeath() != null ? directorEntity.getDateOfDeath().toString() : null,
             directorEntity.getWikipediaLink(), directorEntity.getPhotoUrl());
    }

    public DirectorEntity toEntity() {
        DirectorEntity directorEntity = new DirectorEntity();
        directorEntity.setId(this.id);
        directorEntity.setName(this.name);
        directorEntity.setNationality(this.nationality);
        directorEntity.setDateOfBirth(LocalDate.parse(this.dateOfBirth));
        if (this.dateOfDeath != null) {
            directorEntity.setDateOfDeath(LocalDate.parse(this.dateOfDeath));
        }
        directorEntity.setWikipediaLink(this.wikipediaLink);
        directorEntity.setPhotoUrl(this.photoUrl);
        return directorEntity;
    }
}