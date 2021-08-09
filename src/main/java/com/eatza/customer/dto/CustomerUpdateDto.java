package com.eatza.customer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class CustomerUpdateDto {
	
	private String name;
	private Long phone;
	private Long orderId;
	private Long restaurantId;
	private Long customerId;
	
	public CustomerUpdateDto(String name, Long phone, Long orderId, Long restaurantId, Long customerId) {
		super();
		this.name = name;
		this.phone = phone;
		this.orderId = orderId;
		this.restaurantId = restaurantId;
		this.customerId = customerId;
	}
	
	
		
	
	
}
