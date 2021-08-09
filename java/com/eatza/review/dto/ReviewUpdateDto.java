package com.eatza.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ReviewUpdateDto {
	
	private String reviewDetails;
	private Long rating;
	private Long reviewId;
	private Long orderId;
	private Long restaurantId;
	
	public ReviewUpdateDto(String reviewDetails, Long rating, Long reviewId, Long orderId, Long restaurantId) {
		super();
		this.reviewDetails = reviewDetails;
		this.rating = rating;
		this.reviewId = reviewId;
		this.orderId = orderId;
		this.restaurantId = restaurantId;
	}
	
	
	

	
		
}
