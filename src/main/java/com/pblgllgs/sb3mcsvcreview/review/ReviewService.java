package com.pblgllgs.sb3mcsvcreview.review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    List<Review> getAllReviewsByCompanyId(Long companyId);

    boolean createReview(Long companyId, Review review);

    Optional<Review> getReviewById(Long id);

    Review getReviewByReviewIdByCompanyId(Long reviewId);

    boolean updateReview(Long reviewId, Review review);

    boolean deleteReview(Long reviewId);
}
