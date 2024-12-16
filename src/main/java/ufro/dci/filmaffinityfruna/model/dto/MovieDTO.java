package ufro.dci.filmaffinityfruna.model.dto;

import ufro.dci.filmaffinityfruna.model.entity.MovieEntity;
import ufro.dci.filmaffinityfruna.model.entity.DirectorEntity;
import ufro.dci.filmaffinityfruna.model.entity.GenreEntity;

import java.time.LocalDate;
import java.time.LocalTime;

public record MovieDTO(long id, String name, String synopsis, String country
        , String duration, String releaseYear, String wikipediaLink, String photoUrl
        , long directorId, String genreName, String trailerUrl, String overviewUrl) {

    public MovieDTO(MovieEntity movieEntity) {
        this(movieEntity.getId(), movieEntity.getName(), movieEntity.getSynopsis(), movieEntity.getCountry(),
             movieEntity.getDuration().toString(), movieEntity.getReleaseYear().toString(),
             movieEntity.getWikipediaLink(), movieEntity.getPhotoUrl(),
             movieEntity.getDirector().getId(), movieEntity.getGenre().getName(),
             movieEntity.getTrailerUrl(), movieEntity.getOverviewUrl());
    }

    public MovieEntity toEntity(DirectorEntity director, GenreEntity genre) {
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setId(this.id);
        movieEntity.setName(this.name);
        movieEntity.setSynopsis(this.synopsis);
        movieEntity.setCountry(this.country);
        movieEntity.setDuration(LocalTime.parse(this.duration));
        movieEntity.setReleaseYear(LocalDate.parse(this.releaseYear));
        movieEntity.setWikipediaLink(this.wikipediaLink);
        movieEntity.setPhotoUrl(this.photoUrl);
        movieEntity.setDirector(director);
        movieEntity.setGenre(genre);
        movieEntity.setTrailerUrl(this.trailerUrl);
        movieEntity.setOverviewUrl(this.overviewUrl);
        return movieEntity;
    }
}