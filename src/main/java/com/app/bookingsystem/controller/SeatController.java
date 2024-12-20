package com.app.bookingsystem.controller;

import com.app.bookingsystem.dto.ResponseDTO;
import com.app.bookingsystem.service.SeatService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seat")
public class SeatController {

    private final SeatService seatService;
    public SeatController(SeatService seatService){
        this.seatService=seatService;
    }

    @PostMapping("/create-seat")
    public ResponseDTO createSeat(@RequestParam  final Long busNumber,@RequestParam Long seatNumber){
        return this.seatService.createSeat(busNumber,seatNumber);
    }

    @GetMapping("/retrieve-all-seats")
    public ResponseDTO retrieveAllSeatDetails(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        return this.seatService.retrieveAllSeatDetails(page, size);
    }



}
