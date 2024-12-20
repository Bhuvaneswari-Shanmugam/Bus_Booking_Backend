package com.app.bookingsystem.controller;

import com.app.bookingsystem.dto.DriverDTO;
import com.app.bookingsystem.dto.ResponseDTO;
import com.app.bookingsystem.service.DriverService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/driver")
public class DriverController {

    private final DriverService driverService;
    public DriverController(DriverService driverService){
        this.driverService=driverService;
    }

    @PostMapping("/create-driver")
    public ResponseDTO createDriver(@RequestBody final DriverDTO driverDTO){
        return this.driverService.createDriver(driverDTO);
    }

    @GetMapping("/retrieve-all-driver-details")
    public ResponseDTO retrieveAllDriverDetails(){
        return this.driverService.retrieveAllDriverDetails();
    }
}
