package com.app.booking_system.controller;

import com.app.booking_system.dto.ResponseDTO;
import com.app.booking_system.entity.Seat;
import com.app.booking_system.service.SeatService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seat")
public class SeatController {

    private final SeatService seatService;
    public SeatController(SeatService seatService){
        this.seatService=seatService;
    }

    @PostMapping("/create")
    public ResponseDTO createSeat(@RequestBody Seat seat){
        return this.seatService.createSeat(seat);
    }

    @GetMapping("/fetch")
    public ResponseDTO getAllSeatDetail(){
        return this.seatService.getAllSeatDetail();
    }
}
