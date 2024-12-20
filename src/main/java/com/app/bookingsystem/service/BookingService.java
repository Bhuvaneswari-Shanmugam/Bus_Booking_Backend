package com.app.bookingsystem.service;

import com.app.bookingsystem.config.TokenProvider;
import com.app.bookingsystem.dto.ResponseDTO;
import com.app.bookingsystem.entity.*;
import com.app.bookingsystem.repository.*;
import com.app.bookingsystem.util.Constants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private final TokenProvider tokenProvider;
    private final TripRepository tripRepository;
    private final UserCredentialRepository userCredentialRepository;
    private final BusRepository busRepository;

    public BookingService(BookingRepository bookingRepository,BusService busService,UserCredentialRepository userCredentialRepository,BusRepository busRepository,TokenProvider tokenProvider, TripRepository tripRepository) {
        this.bookingRepository = bookingRepository;
        this.busService = busService;
        this.tokenProvider = tokenProvider;
        this.tripRepository = tripRepository;
        this.userCredentialRepository = userCredentialRepository;
        this.busRepository = busRepository;
    }

    public List<Map<String, Object>> retrieveAllBookingByUserId(final String token) {

        final String userId = tokenProvider.getUserIdFromToken(token);
        final UserCredential user = userCredentialRepository.findUserById(userId);
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
            final String pickupPoint, final String destinationPoint, final String pickupTime, final Long busNumber,
            final String busType,final List<Long> bookedNoOfSeats, final Long perSeatAmount,
            final Long totalAmount, String token) {

        LocalDate pickupDate = LocalDate.parse(pickupTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Instant startOfDay = pickupDate.atStartOfDay(ZoneId.of("Asia/Kolkata")).toInstant();
        Instant endOfDay = pickupDate.plusDays(1).atStartOfDay(ZoneId.of("Asia/Kolkata")).toInstant();

        final  Trip trip = tripRepository.findByPickupPointAndDestinationPointAndPickupTimeBetween(
                pickupPoint, destinationPoint, startOfDay, endOfDay);
        if (trip == null) {
            return ResponseDTO.builder()
                    .message("No trip found for the given parameters.")
                    .statusCode(404)
                    .build();
        }

        final Bus bus = busService.findBusId(busNumber, busType);
        if (bus == null) {
            throw new RuntimeException("Bus not found for the given number and type.");
        }

        final String userId = tokenProvider.getUserIdFromToken(token);
        final UserCredential user = userCredentialRepository.findUserById(userId);
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

    public ResponseDTO deleteBooking(final String token, final Long busNumber, final List<Long> bookedNoOfSeats) {
        final String userId = tokenProvider.getUserIdFromToken(token);
        final UserCredential user = userCredentialRepository.findUserById(userId);
        if (user == null) {
            throw new RuntimeException("Customer not found for the given ID.");
        }
        final Bus bus = busRepository.findByNumber(busNumber);
        if (bus == null) {
            throw new RuntimeException("Bus not found for the given number.");
        }
        final String busId = bus.getId();
        for (Long bookedNoOfSeat : bookedNoOfSeats) {
            Booking booking = bookingRepository.findByUserIdAndBusIdAndBookedNoOfSeatsIn(userId, busId, bookedNoOfSeats);
            if (booking != null) {
                bookingRepository.deleteById(booking.getId());
            }
        }
        return ResponseDTO.builder()
                .message(Constants.DELETED)
                .data(null)
                .statusCode(200)
                .build();
    }
    public ResponseDTO updateBooking(final String authorizationHeader,final String pickupPoint,final String destinationPoint, final String pickupTime, final Long busNumber, final String busType, final List<Long> bookedNoOfSeats,final  Long perSeatAmount,final Long totalAmount, final String token) {

            final LocalDate pickupDate = LocalDate.parse(pickupTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            final Instant startOfDay = pickupDate.atStartOfDay(ZoneId.of("Asia/Kolkata")).toInstant();
            final Instant endOfDay = pickupDate.plusDays(1).atStartOfDay(ZoneId.of("Asia/Kolkata")).toInstant();

            final Trip trip = tripRepository.findByPickupPointAndDestinationPointAndPickupTimeBetween(pickupPoint, destinationPoint, startOfDay, endOfDay);
            if (trip == null) {
                throw new RuntimeException("Trip not found for the given details.");
            }
            final Bus bus = busService.findBusId(busNumber, busType);
            if (bus == null) {
                throw new RuntimeException("Bus not found for the given number and type.");
            }
            final String userId = tokenProvider.getUserIdFromToken(token);
            final UserCredential user = userCredentialRepository.findUserById(userId);
            if (user == null) {
                throw new RuntimeException("Customer not found for the given ID.");
            }
            final Booking booking = bookingRepository.findByUserIdAndBusIdAndBookedNoOfSeatsIn(userId, bus.getId(), bookedNoOfSeats);
            if (booking == null) {
                throw new RuntimeException("Booking not found for the provided user, bus, and seat numbers.");
            }
            booking.setBus(bus);
            booking.setTrip(trip);
            booking.setBookedNoOfSeats(bookedNoOfSeats);
            booking.setPerSeatAmount(perSeatAmount);
            booking.setTotalPrice(totalAmount);

            return ResponseDTO.builder()
                    .message("Booking updated successfully.")
                    .data(this.bookingRepository.save(booking))
                    .statusCode(200)
                    .build();
        }

    public ResponseDTO getAvailableSeats(final Long number) {
        final Bus bus=busRepository.findByNumber(number);
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
    public ResponseDTO retrieveAllBookings(int page, int size) {
        Page<Booking> userPage = this.bookingRepository.findAll(PageRequest.of(page, size));
        return ResponseDTO.builder()
                .message(Constants.RETRIEVED)
                .data(userPage.getContent())
                .statusCode(200)
                .build();
    }
}
