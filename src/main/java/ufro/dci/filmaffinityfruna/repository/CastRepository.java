package ufro.dci.filmaffinityfruna.repository;

import org.springframework.data.repository.CrudRepository;
import ufro.dci.filmaffinityfruna.model.entity.CastEntity;

import java.util.List;

public interface CastRepository extends CrudRepository<CastEntity, Long> {

    List<CastEntity> findByMovieId(Long id);

    List<CastEntity> findByActorId(Long id);
}
