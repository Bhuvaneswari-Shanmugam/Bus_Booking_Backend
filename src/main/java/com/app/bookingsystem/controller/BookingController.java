package com.app.bookingsystem.controller;

import com.app.bookingsystem.dto.ResponseDTO;
import com.app.bookingsystem.service.BookingService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/create-booking")
    public ResponseDTO createBooking(
            @RequestHeader("Authorization") final String authorizationHeader,
            @RequestParam final String pickupPoint,
            @RequestParam final String destinationPoint,
            @RequestParam final String pickupTime,
            @RequestParam final Long busNumber,
            @RequestParam final String busType,
            @RequestParam final List<Long> bookedNoOfSeats,
            @RequestParam final Long perSeatAmount,
            @RequestParam final Long totalAmount) {

        String token = authorizationHeader.substring(7);
        System.err.println("Received parameters: " + pickupPoint + ", " + destinationPoint + ", " + pickupTime + ", " + busNumber + ", " + busType + ", " + bookedNoOfSeats + ", " + perSeatAmount + ", " + totalAmount);

        return this.bookingService.createBooking(pickupPoint, destinationPoint, String.valueOf(pickupTime), busNumber, busType, bookedNoOfSeats, perSeatAmount, totalAmount,token);
    }

    @DeleteMapping("/delete-booking")
    public ResponseDTO deleteBooking(@RequestHeader("Authorization") final String authorizationHeader,
                                     @RequestParam final Long busNumber,
                                     @RequestParam final  List<Long> seatNumbers) {
        String token = authorizationHeader.substring(7);
        return this.bookingService.deleteBooking(token, busNumber, seatNumbers);
    }

    @GetMapping("/retrieve-all-booking-by-userId")
    public List<Map<String, Object>>retrieveAllBookingByUserId(@RequestHeader("Authorization")final String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        return this.bookingService.retrieveAllBookingByUserId(token);
    }

    @PutMapping("/update-booking")
    public ResponseDTO updateBooking(@RequestHeader("Authorization") final String authorizationHeader,
                                     @RequestParam final String pickupPoint,
                                     @RequestParam final String destinationPoint,
                                     @RequestParam final String pickupTime,
                                     @RequestParam final Long busNumber,
                                     @RequestParam final String busType,
                                     @RequestParam final List<Long> bookedNoOfSeats,
                                     @RequestParam final Long perSeatAmount,
                                     @RequestParam final Long totalAmount){
        final String token = authorizationHeader.substring(7);
        return this.bookingService.updateBooking(authorizationHeader,pickupPoint,destinationPoint,pickupTime,busNumber,busType,bookedNoOfSeats,perSeatAmount,totalAmount,token);
    }


    @GetMapping("/available-seat")
    public ResponseDTO getAvailableSeats(@RequestParam final Long number){
        return this.bookingService.getAvailableSeats(number);
    }

    @GetMapping("/retrieve-all-bookings")
    public ResponseDTO retrieveAllBookings(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        return this.bookingService.retrieveAllBookings(page, size);
    }


}
