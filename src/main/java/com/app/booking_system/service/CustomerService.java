package com.app.booking_system.service;

import com.app.booking_system.dto.ResponseDTO;
import com.app.booking_system.entity.Customer;
import com.app.booking_system.repository.CustomerRepository;
import com.app.booking_system.repository.UserCredentialRepository;
import com.app.booking_system.util.Constants;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserCredentialRepository userCredentialRepository;
    public CustomerService(CustomerRepository customerRepository , UserCredentialRepository userCredentialRepository){
        this.customerRepository=customerRepository;
        this.userCredentialRepository=userCredentialRepository;
    }


    public ResponseDTO getAllCustomerDetails(){
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
