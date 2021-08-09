package com.eatza.customer.service.customerService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.eatza.customer.dto.CustomerRequestDto;
import com.eatza.customer.dto.CustomerUpdateDto;
import com.eatza.customer.dto.CustomerUpdateResponseDto;
import com.eatza.customer.dto.ReviewUpdateDto;
import com.eatza.customer.exception.CustomerException;
import com.eatza.customer.model.Customer;
import com.eatza.customer.repository.CustomerRepository;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CustomerServiceTest {


	@Mock
	CustomerRepository customerRepository;
	
	@InjectMocks
	private CustomerServiceImpl customerService;
	
	@Mock
	private RestTemplate restTemplate;
	
	@Test
	public void registerCustomer_basic(){
		CustomerRequestDto customerRequest = new CustomerRequestDto();
		customerRequest.setName("Heelan L");
		customerRequest.setOrderId(1L);
		customerRequest.setPhone(3L);
		customerRequest.setRestaurantId(1L);
		
		Customer savedRecord = new Customer(customerRequest.getName(), customerRequest.getPhone(), customerRequest.getOrderId(), "ACTIVE",customerRequest.getRestaurantId());
		savedRecord.setId(1L);
		
		when(customerRepository.save(any(Customer.class))).thenReturn(savedRecord);

		Customer customer = customerService.registerCustomer(customerRequest);
		assertNotNull(customer);

	}
	
	@Test
	public void deactivateCustomer_basic() {
		Optional<Customer> customer = Optional.of(new Customer("Meena W", 7656754545L, 1L, "ACTIVE", 1L));
		when(customerRepository.findById(anyLong())).thenReturn(customer);
		customer.get().setStatus("DEACTIVATE");
		customer.get().setId(1L);
		when(customerRepository.save(any(Customer.class))).thenReturn(customer.get());
		boolean result = customerService.deactivateCustomer(customer.get().getId());
		assertTrue(result);
		assertEquals("DEACTIVATE", customer.get().getStatus());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void deactivateCustomer_negative() throws Exception {

		Optional<Customer> customer= Optional.empty();
		boolean result = customerService.deactivateCustomer(customer.get().getId());
		assertFalse(result);
	}
	
	@Test
	public void updateCustomer_basic() throws CustomerException {
		CustomerUpdateDto customerUpdateRequest = new CustomerUpdateDto("Leena K", 9987898789L, 1L, 2L, 1L);
		
		Optional<Customer> customer = Optional.of(new Customer("Leena K", 7678765678L, 1L, "ACTIVE", 2L));
		when(customerRepository.findById(anyLong())).thenReturn(customer);
		customer.get().setId(customerUpdateRequest.getCustomerId());
		
		Customer updatedCustomer = new Customer(customerUpdateRequest.getName(), customerUpdateRequest.getPhone(), customerUpdateRequest.getOrderId(), "UPDATE", 2L);
		when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);
	
		CustomerUpdateResponseDto savedCustomer = customerService.updateCustomer(customerUpdateRequest);
		assertEquals("UPDATE", savedCustomer.getStatus());
	}
	
//	@Test
//	public void recivedFeedback_negative() throws CustomerException {
//		//String response = "We got your feedback, details is on console.";
//		ReviewUpdateDto reviewUpdateDto = new ReviewUpdateDto("Good", 3L, 1L);
//		when(restTemplate.put(any(String.class),any(Class.class))).thenReturn(null);
//	}

}
