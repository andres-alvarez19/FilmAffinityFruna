package ufro.dci.filmaffinityfruna.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ufro.dci.filmaffinityfruna.model.entity.GenreEntity;

import java.util.Optional;

@Repository
public interface GenreRepository extends CrudRepository<GenreEntity, Long>{

    Optional<ufro.dci.filmaffinityfruna.model.dto.GenreDTO> findByName (String nombre);

    boolean existsByName (String nombre);

    void deleteByName(String name);
}
