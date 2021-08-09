package com.eatza.review.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eatza.review.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	
	List<Review> findByCustomerId(Long customerId);

}
