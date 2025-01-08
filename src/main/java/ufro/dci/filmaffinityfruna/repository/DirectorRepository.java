package ufro.dci.filmaffinityfruna.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ufro.dci.filmaffinityfruna.model.entity.DirectorEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface DirectorRepository extends CrudRepository<DirectorEntity, Long> {

    Optional<DirectorEntity> findById(long id);

    boolean existsByName(String name);

    List<DirectorEntity> findByName(String name);

    List<DirectorEntity> findByNameContainingIgnoreCase(String name);
}
