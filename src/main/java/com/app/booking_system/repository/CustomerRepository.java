package com.app.booking_system.repository;

import com.app.booking_system.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,String> {


    Optional<Customer> findByEmail(String email);
}
