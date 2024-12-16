package ufro.dci.filmaffinityfruna.model.dto;

import ufro.dci.filmaffinityfruna.model.entity.ReviewEntity;

public record ReviewDTO(long id, String description, Float rating, String reviewDate, long userId, long movieId) {

    public ReviewDTO(ReviewEntity reviewEntity) {
        this(reviewEntity.getId(), reviewEntity.getDescription(), reviewEntity.getRating(),
             reviewEntity.getReviewDate().toString(), reviewEntity.getUser().getId(), reviewEntity.getMovie().getId());
    }
}