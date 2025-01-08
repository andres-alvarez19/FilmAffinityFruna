package ufro.dci.filmaffinityfruna.model.dto;

import java.util.List;

public record CastByDirectorDTO(String movieName, List<ActorDTO> actors) {
}
