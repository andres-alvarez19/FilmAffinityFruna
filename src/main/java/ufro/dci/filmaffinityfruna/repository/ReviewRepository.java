package ufro.dci.filmaffinityfruna.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ufro.dci.filmaffinityfruna.model.entity.ReviewEntity;

import java.util.List;

@Repository
public interface ReviewRepository extends CrudRepository<ReviewEntity, Long> {
    List<ReviewEntity> findByMovieId(Long movieId);

    ReviewEntity findReviewById(Long id);

    ReviewEntity findReviewsByUserId(long userId);

}
