package com.pblgllgs.sb3mcsvcreview.review.impl;
/*
 *
 * @author pblgl
 * Created on 23-10-2024
 *
 */

import com.pblgllgs.sb3mcsvcreview.review.Review;
import com.pblgllgs.sb3mcsvcreview.review.ReviewRepository;
import com.pblgllgs.sb3mcsvcreview.review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getAllReviewsByCompanyId(Long companyId) {
        return reviewRepository.findAllByCompanyId(companyId);
    }

    @Override
    public boolean createReview(Long companyId, Review review) {
        if (companyId != null) {
            review.setCompanyId(companyId);
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    @Override
    public Review getReviewByReviewIdByCompanyId(Long reviewId) {
        return getReviewById(reviewId).orElseThrow(() -> new RuntimeException("NOT_FOUND"));
    }

    @Override
    public boolean updateReview(Long reviewId, Review updatedReview) {
        Optional<Review> optionalReview = getReviewById(reviewId);
        if (optionalReview.isPresent()) {
            Review reviewDB = optionalReview.get();
            reviewDB.setTitle(updatedReview.getTitle());
            reviewDB.setDescription(updatedReview.getDescription());
            reviewDB.setRating(updatedReview.getRating());
            reviewDB.setId(updatedReview.getCompanyId());
            reviewRepository.save(reviewDB);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteReview(Long reviewId) {
        Optional<Review> optionalReview = getReviewById(reviewId);
        Optional<Review> review = reviewRepository.findById(reviewId);
        if (optionalReview.isPresent() && review.isPresent()) {
            reviewRepository.delete(review.get());
            return true;
        }
        return false;
    }
}
