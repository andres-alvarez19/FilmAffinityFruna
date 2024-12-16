package ufro.dci.filmaffinityfruna.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ufro.dci.filmaffinityfruna.model.dto.*;
import ufro.dci.filmaffinityfruna.model.entity.CastEntity;
import ufro.dci.filmaffinityfruna.repository.CastRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CastService {

    private final CastRepository castRepository;

    private final DirectorService directorService;

    public List<CastEntity> getAllCasts() {
        List<CastEntity> casts = new ArrayList<>();
        castRepository.findAll().forEach(casts::add);
        return casts;
    }

    public Optional<CastEntity> getCastById(Long id) {
        return castRepository.findById(id);
    }

    public CastEntity saveCast(CastEntity cast) {
        return castRepository.save(cast);
    }

    public void deleteCast(Long id) {
        castRepository.deleteById(id);
    }

    public List<CastByMovieDTO> getCastByMovieId(Long id) {
        List<CastEntity> casts = castRepository.findByMovieId(id);
        List<CastByMovieDTO> castByMovieDTOS = new ArrayList<>();
        casts.forEach(cast -> castByMovieDTOS.add(new CastByMovieDTO(cast.getId(), cast.getCharacterName(), cast.getActor().getId())));
        return castByMovieDTOS;
    }

    public List<CastByActorDTO> getCastByActorId(Long id) {
        List<CastEntity> casts = castRepository.findByActorId(id);
        List<CastByActorDTO> castByActorDTOS = new ArrayList<>();
        casts.forEach(cast -> castByActorDTOS.add(new CastByActorDTO(cast.getId(), cast.getCharacterName(), new MovieDTO(cast.getMovie()))));
        return castByActorDTOS;
    }

    public List<CastByDirectorDTO> getCastByDirectorId(Long id) {
        directorService.getMoviesByDirectorId(id);
        List<CastByDirectorDTO> casts = new ArrayList<>();
        for (MovieDTO movie : directorService.getMoviesByDirectorId(id)) {
            List<ActorDTO> actors = new ArrayList<>();
            for (CastEntity cast : castRepository.findByMovieId(movie.id())) {
                actors.add(new ActorDTO(cast.getActor()));
            }
            casts.add(new CastByDirectorDTO(movie.name(), actors));

        }
        return casts;
    }
}