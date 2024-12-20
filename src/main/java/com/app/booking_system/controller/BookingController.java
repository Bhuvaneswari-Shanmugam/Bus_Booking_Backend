package com.app.booking_system.controller;

import com.app.booking_system.dto.ResponseDTO;
import com.app.booking_system.entity.Booking;
import com.app.booking_system.service.BookingService;
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

    @PostMapping("/create")
    public ResponseDTO createBooking(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam String pickupPoint,
            @RequestParam String destinationPoint,
            @RequestParam String pickupTime,
            @RequestParam Long busNumber,
            @RequestParam String busType,
            @RequestParam List<Long> bookedNoOfSeats,
            @RequestParam Long perSeatAmount,
            @RequestParam Long totalAmount) {

        String token = authorizationHeader.substring(7);
        System.err.println("Received parameters: " + pickupPoint + ", " + destinationPoint + ", " + pickupTime + ", " + busNumber + ", " + busType + ", " + bookedNoOfSeats + ", " + perSeatAmount + ", " + totalAmount);

        return this.bookingService.createBooking(pickupPoint, destinationPoint, String.valueOf(pickupTime), busNumber, busType, bookedNoOfSeats, perSeatAmount, totalAmount,token);
    }

    @DeleteMapping("/delete")
    public ResponseDTO deleteBooking(@RequestHeader("Authorization") String authorizationHeader,
                                     @RequestParam Long busNumber,
                                     @RequestParam List<Long> seatNumbers) {
        String token = authorizationHeader.substring(7);
        return this.bookingService.deleteBooking(token, busNumber, seatNumbers);
    }

    @GetMapping("/fetch-all-booking-by-userId")
    public List<Map<String, Object>>getAllBookingDetail(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        return this.bookingService.getAllBookingDetail(token);
    }

    @PutMapping("/update-booking")
    public ResponseDTO updateBooking(@RequestHeader("Authorization") String authorizationHeader,
                                     @RequestParam String pickupPoint,
                                     @RequestParam String destinationPoint,
                                     @RequestParam String pickupTime,
                                     @RequestParam Long busNumber,
                                     @RequestParam String busType,
                                     @RequestParam List<Long> bookedNoOfSeats,
                                     @RequestParam Long perSeatAmount,
                                     @RequestParam Long totalAmount){
        String token = authorizationHeader.substring(7);
        return this.bookingService.updateBooking(authorizationHeader,pickupPoint,destinationPoint,pickupTime,busNumber,busType,bookedNoOfSeats,perSeatAmount,totalAmount,token);
    }


    @GetMapping("/available-seat")
    public ResponseDTO getAvailableSeats(@RequestParam Long number){
        return this.bookingService.getAvailableSeats(number);
    }
}
