package ufro.dci.filmaffinityfruna.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ufro.dci.filmaffinityfruna.model.entity.MovieEntity;

import java.util.List;

@Repository
public interface MovieRepository extends CrudRepository<MovieEntity, Long> {

    List<MovieEntity> findByNameContainingIgnoreCase(String title);

    List<MovieEntity> findByName(String title);

    boolean existsByName(String title);

    Iterable<MovieEntity> findTop10ByOrderByRatingDesc();
}
