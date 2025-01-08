package ufro.dci.filmaffinityfruna.model.dto;

import ufro.dci.filmaffinityfruna.model.entity.CastEntity;
import ufro.dci.filmaffinityfruna.model.entity.ActorEntity;
import ufro.dci.filmaffinityfruna.model.entity.MovieEntity;
import ufro.dci.filmaffinityfruna.service.ActorService;
import ufro.dci.filmaffinityfruna.service.MovieService;

public record CastDTO(long id, String characterName, long actorId, long movieId) {

    private static ActorService actorService;
    private static MovieService movieService;

    public CastDTO(CastEntity castEntity) {
        this(castEntity.getId(), castEntity.getCharacterName(), castEntity.getActor().getId(), castEntity.getMovie().getId());
    }

    public CastEntity toEntity() {
        CastEntity castEntity = new CastEntity();
        castEntity.setId(this.id);
        castEntity.setCharacterName(this.characterName);
        castEntity.setActor(fetchActorById(this.actorId));
        castEntity.setMovie(fetchMovieById(this.movieId));
        return castEntity;
    }

    private ActorEntity fetchActorById(long actorId) {
        return actorService.searchById(actorId).toEntity();
    }

    private MovieEntity fetchMovieById(long movieId) {
        return movieService.findMovieById(movieId).toEntity();
    }

    public static void setActorService(ActorService actorService) {
        CastDTO.actorService = actorService;
    }

    public static void setMovieService(MovieService movieService) {
        CastDTO.movieService = movieService;
    }
}