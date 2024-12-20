package com.app.bookingsystem.controller;

import com.app.bookingsystem.dto.ResponseDTO;
import com.app.bookingsystem.dto.TripDTO;
import com.app.bookingsystem.service.TripService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trip")
public class TripController {

    private final TripService tripService;
    public TripController(final TripService tripService){
        this.tripService=tripService;
    }

    @PostMapping("/create")
    public ResponseDTO createTrip(@RequestBody final TripDTO tripDto){
        return this.tripService.createTrip(tripDto);
    }


    @GetMapping("retrieve-trip/{id}")
    public ResponseDTO retrieveTripById(@PathVariable final String id){
        return this.tripService.retrieveTripById(id);
    }
    @GetMapping("retrieve-all-trips")
    public ResponseDTO retrieveAllTrip(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
            return this.tripService.retrieveAllTrip(page, size);
        }

    @GetMapping("/search-trip")
    public boolean searchTrips(@RequestParam final String pickupPoint,
                               @RequestParam final String destinationPoint,
                               @RequestParam final String pickupTime) {

        return tripService.existsTrip(pickupPoint, destinationPoint, pickupTime);
    }

    @PutMapping("/update-trip/{id}")
    public ResponseDTO updateTrip(@PathVariable final String id,@RequestBody final TripDTO tripDto){
        return this.tripService.updateTrip(id,tripDto);
    }

}
