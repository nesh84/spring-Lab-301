package com.eatza.review.service.reviewService;

import java.util.Optional;

import com.eatza.review.dto.ReviewRequestDto;
import com.eatza.review.dto.ReviewUpdateDto;
import com.eatza.review.dto.ReviewUpdateResponseDto;
import com.eatza.review.exception.ReviewException;
import com.eatza.review.model.Review;

public interface ReviewService  {
	
	Review provideReview(ReviewRequestDto reviewRequest);
	Optional<Review> viewReviewRating(Long reviewId);
	ReviewUpdateResponseDto updateReview(ReviewUpdateDto updateReview) throws ReviewException;
}

//This service should help customers:
//To view and provide reviews and ratings for the restaurant
//Customer can also update their reviews and ratings.
