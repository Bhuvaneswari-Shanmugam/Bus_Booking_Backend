package com.app.booking_system.service;

import com.app.booking_system.config.TokenProvider;
import com.app.booking_system.dto.ResponseDTO;
import com.app.booking_system.entity.*;
import com.app.booking_system.repository.*;
import com.app.booking_system.util.Constants;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.Instant;
import java.util.*;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BusService busService;
    private final SeatRepository seatRepository;
    private final TokenProvider tokenProvider;
    private  final TripService tripService;
    private final CustomerService customerService;
    private final TripRepository tripRepository;
    private final UserCredentialRepository userCredentialRepository;
    private final BusRepository busRepository;

    public BookingService(BookingRepository bookingRepository, BusService busService, UserCredentialRepository userCredentialRepository, BusRepository busRepository,SeatRepository seatRepository, TokenProvider tokenProvider, TripService tripService, CustomerService customerService , TripRepository tripRepository) {
        this.bookingRepository = bookingRepository;
        this.busService=busService;
        this.seatRepository=seatRepository;
        this.tokenProvider=tokenProvider;
        this.tripService=tripService;
        this.customerService=customerService;
        this.tripRepository=tripRepository;
        this.userCredentialRepository=userCredentialRepository;
        this.busRepository=busRepository;
    }




    public List<Map<String, Object>> getAllBookingDetail(String token) {

        String userId = tokenProvider.getUserIdFromToken(token);
        System.err.println("Extracted Customer ID: " + userId);

        UserCredential user = userCredentialRepository.findUserById(userId);
        if (user == null) {
            throw new RuntimeException("Customer not found for the given ID.");
        }

        List<Booking> bookings = bookingRepository.findAllByUserId(user.getId());

        List<Map<String, Object>> bookingDetails = new ArrayList<>();

        for (Booking booking : bookings) {
            Map<String, Object> bookingDetail = new HashMap<>();

            bookingDetail.put("busNumber", booking.getBus().getNumber());
            bookingDetail.put("pickupPoint", booking.getTrip().getPickupPoint());
            bookingDetail.put("destinationPoint", booking.getTrip().getDestinationPoint());
            bookingDetail.put("pickupTime", booking.getTrip().getPickupTime());
            bookingDetail.put("bookedNoOfSeats", booking.getBookedNoOfSeats());
            bookingDetail.put("pricePerSeat", booking.getPerSeatAmount());
            bookingDetail.put("totalPrice", booking.getTotalPrice());

            bookingDetails.add(bookingDetail);
        }

        return bookingDetails;
    }



    public ResponseDTO createBooking(
            String pickupPoint, String destinationPoint, String pickupTime, Long busNumber,
            String busType, List<Long> bookedNoOfSeats, Long perSeatAmount,
            Long totalAmount, String token) {

        LocalDate pickupDate = LocalDate.parse(pickupTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Instant startOfDay = pickupDate.atStartOfDay(ZoneId.of("Asia/Kolkata")).toInstant();
        Instant endOfDay = pickupDate.plusDays(1).atStartOfDay(ZoneId.of("Asia/Kolkata")).toInstant();

        Trip trip = tripRepository.findByPickupPointAndDestinationPointAndPickupTimeBetween(
                pickupPoint, destinationPoint, startOfDay, endOfDay);
        System.err.println("trip id; "+ trip.getId());
        if (trip == null) {
            return ResponseDTO.builder()
                    .message("No trip found for the given parameters.")
                    .statusCode(404)
                    .build();
        }

        Bus bus = busService.findBusId(busNumber, busType);
        System.err.println("bus id; "+ bus.getId());
        if (bus == null) {
            throw new RuntimeException("Bus not found for the given number and type.");
        }

        String userId = tokenProvider.getUserIdFromToken(token);
        UserCredential user = userCredentialRepository.findUserById(userId);
        System.err.println("user id; "+ user.getId());
        if (user == null) {
            throw new RuntimeException("Customer not found for the given ID.");
        }

        List<Long> alreadyBookedSeats = new ArrayList<>();
        List<Booking> newBookings = new ArrayList<>();

        for (Long seatNumber : bookedNoOfSeats) {
            boolean isSeatBooked = bookingRepository.existsByBusIdAndTripIdAndBookedNoOfSeatsIn(
                    bus.getId(), trip.getId(), List.of(seatNumber));

            if (isSeatBooked) {
                alreadyBookedSeats.add(seatNumber);
            } else {
                Booking booking = Booking.builder()
                        .bus(bus)
                        .trip(trip)
                        .user(user)
                        .perSeatAmount(perSeatAmount)
                        .bookedNoOfSeats(List.of(seatNumber))
                        .totalPrice(perSeatAmount)
                        .build();
                newBookings.add(booking);
            }
        }
        if (!alreadyBookedSeats.isEmpty()) {
            return ResponseDTO.builder()
                    .message("selected seats are already booked: " + alreadyBookedSeats)
                    .statusCode(400)
                    .build();
        }
        if (!newBookings.isEmpty()) {
            bookingRepository.saveAll(newBookings);
        }

        return ResponseDTO.builder()
                .message("Booking created successfully.")
                .data(newBookings)
                .statusCode(200)
                .build();
    }



    public ResponseDTO deleteBooking(String token, Long busNumber, List<Long> bookedNoOfSeats) {
        String userId = tokenProvider.getUserIdFromToken(token);
        System.err.println("Extracted Customer ID: " + userId);

        UserCredential user = userCredentialRepository.findUserById(userId);
        if (user == null) {
            throw new RuntimeException("Customer not found for the given ID.");
        }

        Bus bus = busRepository.findByNumber(busNumber);
        if (bus == null) {
            throw new RuntimeException("Bus not found for the given number.");
        }

        String busId = bus.getId();
        System.err.println("Extracted Bus ID: " + busId);

        for (Long bookedNoOfSeat : bookedNoOfSeats) {
            Booking booking = bookingRepository.findByUserIdAndBusIdAndBookedNoOfSeatsIn(userId, busId, bookedNoOfSeats);

            if (booking != null) {
                bookingRepository.deleteById(booking.getId());
            } else {
                System.err.println("Booking not found for seat number: " + bookedNoOfSeat);
            }
        }

        return ResponseDTO.builder()
                .message(Constants.DELETED)
                .data(null)
                .statusCode(200)
                .build();
    }



        public ResponseDTO updateBooking(String authorizationHeader, String pickupPoint, String destinationPoint, String pickupTime, Long busNumber, String busType, List<Long> bookedNoOfSeats, Long perSeatAmount, Long totalAmount, String token) {

            LocalDate pickupDate = LocalDate.parse(pickupTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Instant startOfDay = pickupDate.atStartOfDay(ZoneId.of("Asia/Kolkata")).toInstant();
            Instant endOfDay = pickupDate.plusDays(1).atStartOfDay(ZoneId.of("Asia/Kolkata")).toInstant();

            Trip trip = tripRepository.findByPickupPointAndDestinationPointAndPickupTimeBetween(pickupPoint, destinationPoint, startOfDay, endOfDay);
            if (trip == null) {
                throw new RuntimeException("Trip not found for the given details.");
            }

            Bus bus = busService.findBusId(busNumber, busType);
            if (bus == null) {
                throw new RuntimeException("Bus not found for the given number and type.");
            }

            String userId = tokenProvider.getUserIdFromToken(token);
            UserCredential user = userCredentialRepository.findUserById(userId);
            if (user == null) {
                throw new RuntimeException("Customer not found for the given ID.");
            }

            Booking booking = bookingRepository.findByUserIdAndBusIdAndBookedNoOfSeatsIn(userId, bus.getId(), bookedNoOfSeats);
            if (booking == null) {
                throw new RuntimeException("Booking not found for the provided user, bus, and seat numbers.");
            }

            booking.setBus(bus);
            booking.setTrip(trip);
            booking.setBookedNoOfSeats(bookedNoOfSeats);
            booking.setPerSeatAmount(perSeatAmount);
            booking.setTotalPrice(totalAmount);

            bookingRepository.save(booking);

            return ResponseDTO.builder()
                    .message("Booking updated successfully.")
                    .data(booking)
                    .statusCode(200)
                    .build();
        }


    public ResponseDTO getAvailableSeats(Long number) {
        Bus bus=busRepository.findByNumber(number);
        if (bus == null) {
            throw new RuntimeException("Bus not found for the given number and type.");
        }
        List<Booking> booking=bookingRepository.findByBusId(bus.getId());
        return ResponseDTO.builder()
                .message(Constants.RETRIEVED)
                .data(booking)
                .statusCode(200)
                .build();
    }
}
