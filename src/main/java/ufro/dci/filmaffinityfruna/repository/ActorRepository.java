package ufro.dci.filmaffinityfruna.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ufro.dci.filmaffinityfruna.model.entity.ActorEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface ActorRepository extends CrudRepository<ActorEntity, Long> {

    Set<ActorEntity> findByNationality(String country);

    Set<ActorEntity> findByDateOfBirth(LocalDate date);

    boolean existsByName(String name);

    boolean existsByNameAndDateOfBirth(String name, LocalDate dateOfBirth);

    List<ActorEntity> findByName(String name);
}
