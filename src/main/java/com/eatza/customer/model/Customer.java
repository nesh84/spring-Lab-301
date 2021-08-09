package com.eatza.customer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="customer")
@Getter @Setter @NoArgsConstructor
public class Customer {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Long phone;
	private Long orderId;
	private String status;
	private Long restaurantId;
	
	
	public Customer(String name, Long phone, Long orderId, String status, Long restaurantId) {
		this.name = name;
		this.phone = phone;
		this.orderId = orderId;
		this.status = status;
		this.restaurantId = restaurantId;
	}
	
	
	

	
}
