package ufro.dci.filmaffinityfruna.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ufro.dci.filmaffinityfruna.model.entity.ActorEntity;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ActorRepository extends CrudRepository<ActorEntity, Long> {

    List<ActorEntity> findByNameContainingIgnoreCase(String name);

    boolean existsByName(String name);

    boolean existsByNameAndDateOfBirth(String name, LocalDate dateOfBirth);

    List<ActorEntity> findByName(String name);
}
