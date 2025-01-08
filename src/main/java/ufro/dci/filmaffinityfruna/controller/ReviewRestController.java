package ufro.dci.filmaffinityfruna.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufro.dci.filmaffinityfruna.model.dto.ReviewDTO;
import ufro.dci.filmaffinityfruna.model.entity.ReviewEntity;
import ufro.dci.filmaffinityfruna.service.ReviewService;
import ufro.dci.filmaffinityfruna.utils.MessageConstant;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/review")
public class ReviewRestController {

    private final ReviewService reviewService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid ReviewEntity reviewEntity) {
        reviewService.register(reviewEntity);
        return new ResponseEntity<>(MessageConstant.REGISTERED, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReviewById(@PathVariable(name = "id") Long id) {
        reviewService.deleteReviewById(id);
        return new ResponseEntity<>(MessageConstant.DELETED, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ReviewDTO> findReviewById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(reviewService.findReviewById(id), HttpStatus.OK);
    }

    @GetMapping("/find/user/{userId}")
    public ResponseEntity<ReviewDTO> findReviewsByUserId(@PathVariable(name = "userId") Long userId) {
        return new ResponseEntity<>(reviewService.findReviewsByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        return new ResponseEntity<>(reviewService.getAllReviews(), HttpStatus.OK);
    }
}
