package com.app.bookingsystem.controller;

import com.app.bookingsystem.dto.BusDTO;
import com.app.bookingsystem.dto.ResponseDTO;
import com.app.bookingsystem.service.BusService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bus")
public class BusController {

    private final BusService busService;
    public BusController(BusService busService){
        this.busService=busService;
    }

    @PostMapping("/create-bus")
    public ResponseDTO createBus(@RequestBody final BusDTO busDTO){
        return this.busService.createBus(busDTO);
    }

    @GetMapping("/retrieve-all-bus")
    public ResponseDTO retrieveAllBuses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return this.busService.retrieveAllBuses(page, size);
    }

    @GetMapping("/retrieve-bus/{id}")
    public ResponseDTO retrieveBusById(@PathVariable final String id){
        return this.busService.retrieveBusById(id);
    }

    @PutMapping("/update-bus/{id}")
    public ResponseDTO updateBus(@PathVariable final String id, @RequestBody final  BusDTO busDTO) {
        return this.busService.updateBus(id, busDTO);
    }

    @GetMapping("/search-bus")
    public Long getAvailableSeatCount(
            @RequestParam Long number,
            @RequestParam String type) {
        return this.busService.getAvailableSeatCount(number, type);
    }
}
