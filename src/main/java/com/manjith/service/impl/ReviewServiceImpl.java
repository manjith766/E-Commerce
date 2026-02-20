package com.manjith.service.impl;

import com.manjith.entity.Product;
import com.manjith.entity.Review;
import com.manjith.entity.User;
import com.manjith.repository.ReviewRepository;
import com.manjith.request.CreateReviewRequest;
import com.manjith.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.EmptyStackException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public Review createReview(CreateReviewRequest request, User user, Product product) {
        Review review= new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setReviewText(request.getReviewText());
        review.setRating(request.getReviewRating());
        review.setProductImages(request.getProductImages());

        product.getReviews().add(review);
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewByProductId(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    @Override
    public Review updateReview(Long reviewId, String reviewTax, double rating, Long userId) throws Exception {
        Review review = getReviewById(reviewId);
        if(review.getUser().getId().equals(userId)){
            review.setReviewText(reviewTax);
            review.setRating(rating);
            return reviewRepository.save(review);
        }
        throw  new Exception("you can,t update this review");
    }

    @Override
    public void deleteReview(Long reviewId, Long userId) throws Exception {

        Review review = getReviewById(reviewId);
        if (review.getUser().getId().equals(userId)){
            throw new Exception("you cannot delete this review");
        }
        reviewRepository.delete(review);


    }

    @Override
    public Review getReviewById(Long reviewId) throws Exception {
        return reviewRepository.findById(reviewId).orElseThrow(()->new Exception("review not found"));
    }
}
