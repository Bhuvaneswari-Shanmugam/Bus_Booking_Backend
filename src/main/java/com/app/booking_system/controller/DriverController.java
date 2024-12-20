package com.app.booking_system.controller;

import com.app.booking_system.dto.DriverDTO;
import com.app.booking_system.dto.ResponseDTO;
import com.app.booking_system.service.DriverService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/driver")
public class DriverController {

    private final DriverService driverService;
    public DriverController(DriverService driverService){
        this.driverService=driverService;
    }

    @PostMapping("/create")
    public ResponseDTO createDriver(@RequestBody DriverDTO driverDto){
        return this.driverService.createDriver(driverDto);
    }

    @GetMapping("/fetch")
    public ResponseDTO getAllDriverDetail(){
        return this.driverService.getAllDriverDetail();
    }
}
