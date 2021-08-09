package com.eatza.customer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eatza.customer.dto.CustomerRequestDto;
import com.eatza.customer.dto.CustomerUpdateDto;
import com.eatza.customer.dto.CustomerUpdateResponseDto;
import com.eatza.customer.dto.ReviewUpdateDto;
import com.eatza.customer.exception.CustomerException;
import com.eatza.customer.model.Customer;
import com.eatza.customer.service.customerService.CustomerService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RefreshScope
@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@Autowired
	private Environment env;

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@PostMapping
	public ResponseEntity<Customer> registerCustomer(@RequestBody CustomerRequestDto customerRequestDto) {
		logger.debug("In register customer method, calling the service");
		Customer customer = customerService.registerCustomer(customerRequestDto);
		logger.debug("Customer registerd Successfully");
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(customer);

	}

	@GetMapping("/deactiavte/{customerId}")
	public ResponseEntity<String> deactiavteCustomer(@PathVariable Long customerId) throws CustomerException {
		logger.debug("In status change method");
		boolean result =customerService.deactivateCustomer(customerId);
		if(result) {
			logger.debug("Customer status changed Successfully");
			return ResponseEntity
					.status(HttpStatus.OK)
					.body("Customer status changed Successfully"); 
		} else {
			logger.debug("No records found for respective id");
			throw new CustomerException("No records found for respective id");
		}	

	}

	@PutMapping("/update")
	public ResponseEntity<CustomerUpdateResponseDto> updateCustomer(@RequestBody CustomerUpdateDto customerUpdateDto) throws CustomerException {

		logger.debug(" In updateCustomer method, calling service");
		CustomerUpdateResponseDto updatedResponse = customerService.updateCustomer(customerUpdateDto);
		logger.debug("Returning back the object");

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(updatedResponse);


	}

	// customer can update their rating based on customer id
	@HystrixCommand(fallbackMethod = "fallBackGetAndUpdateReview")
	@PutMapping("/update/rating")
	public ResponseEntity<String>  getAndUpdateReview(@RequestBody ReviewUpdateDto reviewUpdateDto) throws CustomerException{
		logger.debug("In getAndUpdateReview by customerId method, calling rating usting rest");
		String response = customerService.recivedFeedback(reviewUpdateDto);
		logger.debug("Returning back the review response from controller");

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(response);

	}

	// To test refress scope with config
	@GetMapping("/cloud")
	public ResponseEntity<String> getHello(Model model) {   
		return new ResponseEntity<>( env.getProperty("message"), HttpStatus.OK);
	}

	public ResponseEntity<String> fallBackGetAndUpdateReview(@RequestBody ReviewUpdateDto reviewUpdateDto) throws CustomerException{
		return ResponseEntity
				.status(HttpStatus.BAD_GATEWAY)
				.body("Error while accesting as depended review-service"+ reviewUpdateDto);
	}

}
