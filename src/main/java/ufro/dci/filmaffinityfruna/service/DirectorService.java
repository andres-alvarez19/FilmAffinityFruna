package ufro.dci.filmaffinityfruna.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ufro.dci.filmaffinityfruna.model.dto.DirectorDTO;
import ufro.dci.filmaffinityfruna.model.dto.MovieDTO;
import ufro.dci.filmaffinityfruna.model.entity.DirectorEntity;
import ufro.dci.filmaffinityfruna.repository.DirectorRepository;
import ufro.dci.filmaffinityfruna.utils.MessageConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DirectorService {

    private final DirectorRepository directorRepository;

    public void register(DirectorEntity directorEntity) {
        if (directorRepository.existsByName(directorEntity.getName())) {
            throw new IllegalArgumentException("El nombre del director ya está registrado");
        } else {
            directorRepository.save(directorEntity);
        }
    }

    public void update(long id, DirectorEntity modifiedDirector) {
        Optional<DirectorEntity> optionalDirector = directorRepository.findById(id);
        if (directorRepository.existsByName(modifiedDirector.getName())) {
            throw new IllegalArgumentException("El nombre del director ya está registrado");
        }else{
            if (optionalDirector.isPresent()) {
                DirectorEntity director = optionalDirector.get();
                director.setName(modifiedDirector.getName());
                director.setNationality(modifiedDirector.getNationality());
                director.setDateOfBirth(modifiedDirector.getDateOfBirth());
                director.setDateOfDeath(modifiedDirector.getDateOfDeath());
                director.setWikipediaLink(modifiedDirector.getWikipediaLink());
                directorRepository.save(director);
            } else {
                throw new IllegalArgumentException(MessageConstant.NOT_FOUND);
            }
        }
    }

    public void deleteDirectorById(long id) {
        if(!directorRepository.existsById(id)){
            throw new IllegalArgumentException(MessageConstant.NOT_FOUND);
        }else{
            directorRepository.deleteById(id);
        }
    }

    public List<DirectorEntity> searchByName(String name) {
        if(!directorRepository.existsByName(name)){
            throw new IllegalArgumentException(MessageConstant.NOT_FOUND);
        }else{
            return directorRepository.findByName(name);
        }
    }

    public List<DirectorDTO> searchByNameIgnoreCase(String name) {
        List<DirectorDTO> directors = new ArrayList<>();
        directorRepository.findByNameContainingIgnoreCase(name).forEach(directorEntity -> directors.add(new DirectorDTO(directorEntity)));
        return directors;
    }

    public List<DirectorDTO> getAllDirectors() {
        List<DirectorDTO> directors = new ArrayList<>();
        directorRepository.findAll().forEach(directorEntity -> directors.add(new DirectorDTO(directorEntity)));
        return directors;
    }

    public DirectorDTO searchById(Long id) {
        return directorRepository.findById(id)
                .map(DirectorDTO::new)
                .orElseThrow(() -> new IllegalArgumentException(MessageConstant.NOT_FOUND));
    }

    public List<MovieDTO> getMoviesByDirectorId(Long id) {
        return directorRepository.findById(id)
                .map(DirectorEntity::getMoviesDirected)
                .map(movies -> {
                    List<MovieDTO> movieDTOS = new ArrayList<>();
                    movies.forEach(movieEntity -> movieDTOS.add(new MovieDTO(movieEntity)));
                    return movieDTOS;
                })
                .orElseThrow(() -> new IllegalArgumentException(MessageConstant.NOT_FOUND));
    }
}