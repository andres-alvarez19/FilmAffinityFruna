package ufro.dci.filmaffinityfruna.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ufro.dci.filmaffinityfruna.model.entity.GenreEntity;

@Repository
public interface GenreRepository extends CrudRepository<GenreEntity, Long>{

    GenreEntity findByName (String nombre);

    boolean existsByName (String nombre);

    void deleteByName(String name);
}
