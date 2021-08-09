package com.eatza.customer.service.customerService;

import com.eatza.customer.dto.CustomerRequestDto;
import com.eatza.customer.dto.CustomerUpdateDto;
import com.eatza.customer.dto.CustomerUpdateResponseDto;
import com.eatza.customer.dto.ReviewUpdateDto;
import com.eatza.customer.exception.CustomerException;
import com.eatza.customer.model.Customer;

public interface CustomerService  {
	
	Customer registerCustomer(CustomerRequestDto customerRequest);
	boolean deactivateCustomer(Long customerId);
	String recivedFeedback(ReviewUpdateDto reviewUpdateDto)throws CustomerException;
	CustomerUpdateResponseDto updateCustomer(CustomerUpdateDto customerUpdateDto) throws CustomerException;

}
