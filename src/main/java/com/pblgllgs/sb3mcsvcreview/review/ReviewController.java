package com.pblgllgs.sb3mcsvcreview.review;
/*
 *
 * @author pblgl
 * Created on 23-10-2024
 *
 */

import com.pblgllgs.sb3mcsvcreview.messaging.ReviewMessageProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMessageProducer reviewMessageProducer;

    public ReviewController(ReviewService reviewService, ReviewMessageProducer reviewMessageProducer) {
        this.reviewService = reviewService;
        this.reviewMessageProducer = reviewMessageProducer;
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
            reviewMessageProducer.sendMessage(review);
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

    @GetMapping("/averageRating")
    public Double getAverageReview(@RequestParam Long companyId) {
        List<Review> allReviewsByCompanyId = reviewService.getAllReviewsByCompanyId(companyId);
        return allReviewsByCompanyId.stream().mapToDouble(Review::getRating).average().orElse(0.0);
    }
}
