package ufro.dci.filmaffinityfruna.model.dto;

import ufro.dci.filmaffinityfruna.model.entity.GenreEntity;

public record GenreDTO(String name, String description) {

    public static GenreDTO fromEntity(GenreEntity genreEntity) {
        return new GenreDTO(genreEntity.getName(), genreEntity.getDescription());
    }

    public GenreEntity toEntity() {
        GenreEntity genreEntity = new GenreEntity();
        genreEntity.setName(this.name);
        genreEntity.setDescription(this.description);
        return genreEntity;
    }
}