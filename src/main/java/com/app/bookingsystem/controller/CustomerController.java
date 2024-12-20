package com.app.bookingsystem.controller;

import com.app.bookingsystem.dto.ResponseDTO;
import com.app.bookingsystem.service.CustomerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;
    public CustomerController(CustomerService customerService){
        this.customerService=customerService;
    }


    @GetMapping("/retrieve-customer-detail")
    public ResponseDTO retrieveAllCustomerDetails(){
        return this.customerService.retrieveAllCustomerDetails();
    }
}
