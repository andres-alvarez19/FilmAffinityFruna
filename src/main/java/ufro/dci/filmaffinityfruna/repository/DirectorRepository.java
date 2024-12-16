package ufro.dci.filmaffinityfruna.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ufro.dci.filmaffinityfruna.model.dto.DirectorDTO;
import ufro.dci.filmaffinityfruna.model.entity.DirectorEntity;

import java.util.List;

@Repository
public interface DirectorRepository extends CrudRepository<DirectorEntity, Long> {
    boolean existsByName(String name);

    List<DirectorEntity> findByName(String name);

    DirectorDTO findDirectorById(Long id);
}
