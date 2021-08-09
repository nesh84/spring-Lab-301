package com.eatza.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eatza.customer.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
