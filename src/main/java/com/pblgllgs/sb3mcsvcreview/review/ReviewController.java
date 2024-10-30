package com.pblgllgs.sb3mcsvcreview.review;
/*
 *
 * @author pblgl
 * Created on 23-10-2024
 *
 */

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviewByCompany(
            @RequestParam Long companyId
    ) {
        return new ResponseEntity<>(reviewService.getAllReviewsByCompanyId(companyId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createReview(
            @RequestParam Long companyId,
            @RequestBody Review review
    ) {
        boolean success = reviewService.createReview(companyId, review);
        if (success) {
            return new ResponseEntity<>("Review added successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Review cant be created because company dont exists", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewByIdAndCompanyId(
            @PathVariable Long reviewId
    ) {
        return new ResponseEntity<>(reviewService.getReviewByReviewIdByCompanyId(reviewId),HttpStatus.OK);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(
            @PathVariable Long reviewId,
            @RequestBody Review review
    ) {
        boolean success = reviewService.updateReview( reviewId, review);
        if (success) {
            return new ResponseEntity<>("Review updated successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Review cant be updated because company dont exists", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        boolean success = reviewService.deleteReview(reviewId);
        if (success) {
            return new ResponseEntity<>("Review deleted successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Review cant be deleted", HttpStatus.BAD_REQUEST);
    }
}
