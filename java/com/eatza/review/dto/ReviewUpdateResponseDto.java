package com.eatza.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ReviewUpdateResponseDto  {

	
	private Long reviewId;
	private String reviewDetails;
	private Long rating;
	private Long orderId;
	private Long restaurantId;
	private Long customerId;
	
	public ReviewUpdateResponseDto(Long reviewId, String reviewDetails, Long rating, Long orderId, Long restaurantId,
			Long customerId) {
		super();
		this.reviewId = reviewId;
		this.reviewDetails = reviewDetails;
		this.rating = rating;
		this.orderId = orderId;
		this.restaurantId = restaurantId;
		this.customerId = customerId;
	}
	
	
	

	
}
