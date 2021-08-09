package com.eatza.customer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class CustomerRequestDto {
	
	private String name;
	private Long phone;
	private Long orderId;
	private Long restaurantId;
	
	

}
