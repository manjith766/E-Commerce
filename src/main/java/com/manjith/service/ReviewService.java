package com.manjith.service;

import com.manjith.entity.Product;
import com.manjith.entity.Review;
import com.manjith.entity.User;
import com.manjith.request.CreateReviewRequest;

import java.util.List;

public interface ReviewService {


    Review createReview(CreateReviewRequest request, User user, Product product);

    List<Review>getReviewByProductId(Long productId);

    Review updateReview(Long reviewId,String reviewTax,double rating,Long userId) throws Exception;

    void deleteReview(Long reviewId,Long userId) throws Exception;

    Review getReviewById(Long reviewId) throws Exception;
}
