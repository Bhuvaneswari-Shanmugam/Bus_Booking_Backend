package com.app.booking_system.controller;

import com.app.booking_system.dto.BusDTO;
import com.app.booking_system.dto.ResponseDTO;
import com.app.booking_system.service.BusService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bus")
public class BusController {

    private final BusService busService;
    public BusController(BusService busService){
        this.busService=busService;
    }

    @PostMapping("/create")
    public ResponseDTO createBus(@RequestBody BusDTO busDto){
        return this.busService.createBus(busDto);
    }

    @GetMapping("/fetch-all-bus")
    public ResponseDTO getAllBusDetails(){
        return this.busService.getAllBusDetails();
    }

    @GetMapping("/fetch-bus/{id}")
    public ResponseDTO getBusById(@PathVariable String id){
        return this.busService.getBusById(id);
    }

    @PutMapping("/update-bus/{id}")
    public ResponseDTO updateBus(@PathVariable String id, @RequestBody BusDTO busDto) {
        return this.busService.updateBus(id, busDto);
    }


//    @GetMapping("/search")
//    public Long getAvaliableSeatCount(@RequestParam String id){
//        return this.busService.getAvailableSeatCount(id);
//    }



    @GetMapping("/search")
    public Long getAvailableSeatCount(
            @RequestParam Long number,
            @RequestParam String type) {
        return this.busService.getAvailableSeatCount(number, type);
    }
}
