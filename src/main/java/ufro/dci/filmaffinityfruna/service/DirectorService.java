package ufro.dci.filmaffinityfruna.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ufro.dci.filmaffinityfruna.model.entity.DirectorEntity;
import ufro.dci.filmaffinityfruna.repository.DirectorRepository;

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
                throw new IllegalArgumentException("Director no encontrado");
            }
        }
    }

    public void deleteDirectorById(long id) {
        if(!directorRepository.existsById(id)){
            throw new IllegalArgumentException("Director no encontrado");
        }else{
            directorRepository.deleteById(id);
        }
    }

    public List<DirectorEntity> searchByName(String name) {
        if(!directorRepository.existsByName(name)){
            throw new IllegalArgumentException("Director no encontrado");
        }else{
            return directorRepository.findByName(name);
        }
    }

}