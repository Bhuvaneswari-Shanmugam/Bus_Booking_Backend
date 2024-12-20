package com.app.bookingsystem.service;

import com.app.bookingsystem.dto.ResponseDTO;
import com.app.bookingsystem.entity.Customer;
import com.app.bookingsystem.repository.CustomerRepository;
import com.app.bookingsystem.util.Constants;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository=customerRepository;
    }

    public ResponseDTO retrieveAllCustomerDetails(){
        return ResponseDTO.builder()
                .message(Constants.RETRIEVED)
                .data(this.customerRepository.findAll())
                .statusCode(200)
                .build();
    }

    public Customer findCustomerById(String customerId) {
        return customerRepository.findById(customerId).orElse(null);
    }
}
