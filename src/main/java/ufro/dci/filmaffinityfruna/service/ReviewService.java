package ufro.dci.filmaffinityfruna.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ufro.dci.filmaffinityfruna.model.dto.ReviewDTO;
import ufro.dci.filmaffinityfruna.model.entity.ReviewEntity;
import ufro.dci.filmaffinityfruna.repository.ReviewRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public void deleteReviewById(long id) {
        if (!reviewRepository.existsById(id)) {
            throw new IllegalArgumentException("Reseña no encontrada");
        } else {
            reviewRepository.deleteById(id);
        }
    }

    public ReviewDTO findReviewById(long id) {
         return new ReviewDTO(reviewRepository.findReviewById(id));
    }

    public ReviewDTO findReviewsByUserId(long userId) {
         return new ReviewDTO(reviewRepository.findReviewsByUserId(userId));
    }

    public List<ReviewDTO> getAllReviews() {
        List<ReviewDTO> reviews = new ArrayList<>();
        reviewRepository.findAll().forEach(review -> reviews.add(new ReviewDTO(review)));
        return reviews;
    }

    public void register(ReviewEntity reviewEntity) {
        if (reviewRepository.existsById(reviewEntity.getId())) {
            throw new IllegalArgumentException("La reseña ya está registrada");
        } else if (reviewEntity.getRating() > 10 || reviewEntity.getRating() < 0) {
            throw new IllegalArgumentException("La calificación debe estar entre 0 y 10");
        }else {
            reviewRepository.save(reviewEntity);
        }
    }
}
