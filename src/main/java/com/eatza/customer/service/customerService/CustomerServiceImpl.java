package com.eatza.customer.service.customerService;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.eatza.customer.dto.CustomerRequestDto;
import com.eatza.customer.dto.CustomerUpdateDto;
import com.eatza.customer.dto.CustomerUpdateResponseDto;
import com.eatza.customer.dto.ReviewUpdateDto;
import com.eatza.customer.exception.CustomerException;
import com.eatza.customer.model.Customer;
import com.eatza.customer.repository.CustomerRepository;


@Service
public class CustomerServiceImpl implements CustomerService {


	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${review.rating.update.url}")
	private String reviewRatingUpdateUrl;


	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Override
	public Customer registerCustomer(CustomerRequestDto customerRequest) {
		logger.debug("In register customer method, calling repo");
		Customer customer = new Customer(customerRequest.getName(), customerRequest.getPhone(), customerRequest.getOrderId(), "ACTIVE", customerRequest.getRestaurantId());
		Customer regCustomer = customerRepository.save(customer);
		logger.debug("Saved customer to db");
		return regCustomer;
	}

	@Override
	public boolean deactivateCustomer(Long customerId) {
		logger.debug("In deactivate customer service method, calling repository");
		Optional<Customer> customer = customerRepository.findById(customerId);
		if(customer.isPresent()) {
			logger.debug("Customer was found in db");
			customer.get().setStatus("DEACTIVATE");
			customerRepository.save(customer.get());
			return true;
		}
		else {
			logger.debug("Customer not found");
			return false;
		}	

	}


	@Override
	public CustomerUpdateResponseDto updateCustomer(CustomerUpdateDto customerUpdateRequest) throws CustomerException {
		Customer customer = new Customer(customerUpdateRequest.getName(), customerUpdateRequest.getPhone(), customerUpdateRequest.getOrderId(), "UPDATED", customerUpdateRequest.getRestaurantId());
		Optional<Customer> previouslyPersistedData = customerRepository.findById(customerUpdateRequest.getCustomerId());

		if(!previouslyPersistedData.isPresent()) {
			throw new CustomerException("Update Failed, respective customer not found");
		}
		if(!(customerUpdateRequest.getRestaurantId().equals(previouslyPersistedData.get().getRestaurantId() ))) {
			throw new CustomerException("Update Failed, cannot change restaurants while updating customer");

		}
		customer.setId(previouslyPersistedData.get().getId());
		Customer savedCustomer = customerRepository.save(customer);
		return new CustomerUpdateResponseDto(savedCustomer.getId(), savedCustomer.getName(), savedCustomer.getPhone(), savedCustomer.getOrderId(), savedCustomer.getStatus(), savedCustomer.getRestaurantId());

	}

	@Override
	public String recivedFeedback(ReviewUpdateDto reviewUpdateDto) throws CustomerException {
		logger.debug("In getAndUpdateReview by customerId method, calling rating usting rest");
		try {
			restTemplate.put(reviewRatingUpdateUrl, reviewUpdateDto, String.class);
			return "We got your feedback, Please check your mail box for detais.";
		} 
		catch(ResourceAccessException e) {
			throw new CustomerException("Something went wrong, looks like review service not working");
		}			
	}

}
