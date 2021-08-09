package com.eatza.customer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class CustomerUpdateResponseDto  {

	
	private Long customerId;
	private String name;
	private Long phone;
	private Long orderId;
	private String status;
	private Long restaurantId;
	
	
	public CustomerUpdateResponseDto(Long customerId, String name, Long phone, Long orderId, String status,
			Long restaurantId) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.phone = phone;
		this.orderId = orderId;
		this.status = status;
		this.restaurantId = restaurantId;
	}
	
	

	
}
