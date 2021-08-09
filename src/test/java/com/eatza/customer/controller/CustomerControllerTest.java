package com.eatza.customer.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.eatza.customer.dto.CustomerRequestDto;
import com.eatza.customer.dto.CustomerUpdateDto;
import com.eatza.customer.dto.CustomerUpdateResponseDto;
import com.eatza.customer.dto.ReviewUpdateDto;
import com.eatza.customer.model.Customer;
import com.eatza.customer.service.customerService.CustomerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value= CustomerController.class)
public class CustomerControllerTest {

	@MockBean
	CustomerServiceImpl customerService;
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void registerCustomer() throws Exception {
		CustomerRequestDto customerRequestDto = new CustomerRequestDto();
		customerRequestDto.setName("Rohan M");
		customerRequestDto.setOrderId(8897654345L);
		customerRequestDto.setPhone(1L);
		customerRequestDto.setRestaurantId(1L);
		
		Customer customer = new Customer("Rohan M", 8897654345L, 1L, "ACTIVE", 1L);
		when(customerService.registerCustomer(any(CustomerRequestDto.class))).thenReturn(customer);
		customer.setId(1L);
		RequestBuilder request = MockMvcRequestBuilders.post(
				"/customer")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString((customerRequestDto)));
		mockMvc.perform(request)
		.andExpect(status().is(200))
		.andReturn();
	}
	

	@Test
	public void updateCustomer() throws Exception{
		
		CustomerUpdateDto customerUpdateDto = new CustomerUpdateDto("Rahul K", 2233445678L, 1L, 1L, 2L);
		CustomerUpdateResponseDto updatedRecord = new CustomerUpdateResponseDto(2L, "Rahul k", 2233445678L, 1L, "UPATED", 1L);
		when(customerService.updateCustomer(any(CustomerUpdateDto.class))).thenReturn(updatedRecord);
		RequestBuilder request = MockMvcRequestBuilders.put(
				"/customer/update")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString((customerUpdateDto)));
		mockMvc.perform(request)
		.andExpect(status().is(200))
		.andReturn();

	}
	
	@Test
	public void deactiavteCustomer() throws Exception {

		when(customerService.deactivateCustomer(anyLong())).thenReturn(true);
		RequestBuilder request = MockMvcRequestBuilders.get(
				"/customer/deactiavte/1")
				.accept(MediaType.ALL);
		mockMvc.perform(request)
		.andExpect(status().is(200))
		.andReturn();

	}

	@Test
	public void deactiavteCustomer_negative() throws Exception {

		when(customerService.deactivateCustomer(anyLong())).thenReturn(false);
		RequestBuilder request = MockMvcRequestBuilders.get(
				"/customer/deactiavte/1")
				.accept(MediaType.ALL);
		mockMvc.perform(request)
		.andExpect(status().is(400))
		.andReturn();
	}
		
		@Test
		public void getAndUpdateReview_negative() throws Exception {

			when(customerService.recivedFeedback(any(ReviewUpdateDto.class))).thenReturn(null);
			RequestBuilder request = MockMvcRequestBuilders.get(
					"/customer/update/rating")
					.accept(MediaType.ALL);
			mockMvc.perform(request)
			.andExpect(status().is(405))
			.andReturn();

		}
	
}
