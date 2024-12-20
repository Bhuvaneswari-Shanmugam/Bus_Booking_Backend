package com.app.booking_system.service;

import com.app.booking_system.dto.ResponseDTO;
import com.app.booking_system.dto.TripDTO;
import com.app.booking_system.entity.Trip;
import com.app.booking_system.repository.OrganizationRepository;
import com.app.booking_system.repository.TripRepository;
import com.app.booking_system.util.Constants;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TripService {

    private final TripRepository tripRepository;
    private final OrganizationRepository organizationRepository;


    public TripService(TripRepository tripRepository, OrganizationRepository organizationRepository) {
        this.tripRepository = tripRepository;
        this.organizationRepository = organizationRepository;

    }

    public ResponseDTO createTrip(TripDTO tripDTO) {
        Trip savedTrip = Trip.builder()
                .destinationPoint(tripDTO.getDestinationPoint())
                .pickupPoint(tripDTO.getPickupPoint())
                .pickupTime(tripDTO.getPickupTime())
                .reachingTime(tripDTO.getReachingTime())
                .expense(tripDTO.getExpense())
                .build();

        return ResponseDTO.builder()
                .message(Constants.CREATED)
                .data(this.tripRepository.save(savedTrip))
                .statusCode(200)
                .build();
    }

    public ResponseDTO getAllTripDetails() {
        List<Trip> trips = tripRepository.findAll();

        return ResponseDTO.builder()
                .message(Constants.RETRIEVED)
                .data(trips)
                .statusCode(200)
                .build();
    }

    public boolean existsTrip(String pickupPoint, String destinationPoint, String pickupTime) {

        LocalDate pickupDate = LocalDate.parse(pickupTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Instant startOfDay = pickupDate.atStartOfDay(ZoneId.of("Asia/Kolkata")).toInstant();
        Instant endOfDay = pickupDate.plusDays(1).atStartOfDay(ZoneId.of("Asia/Kolkata")).toInstant();

        System.err.println(tripRepository.existsByPickupPointAndDestinationPointAndPickupTimeBetween(
                pickupPoint, destinationPoint, startOfDay, endOfDay));
        return tripRepository.existsByPickupPointAndDestinationPointAndPickupTimeBetween(
                pickupPoint, destinationPoint, startOfDay, endOfDay
        );
    }

    private TripDTO convertToTripDTO(Trip trip) {
        return TripDTO.builder()
                .pickupPoint(trip.getPickupPoint())
                .destinationPoint(trip.getDestinationPoint())
                .pickupTime(trip.getPickupTime())
                .reachingTime(trip.getReachingTime())
                .expense(trip.getExpense())
                .build();
    }

    public Trip findTrip(String pickupPoint, String destinationPoint, String pickupTime) {

        LocalDate pickupDate = LocalDate.parse(pickupTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Instant startOfDay = pickupDate.atStartOfDay(ZoneId.of("Asia/Kolkata")).toInstant();
        Instant endOfDay = pickupDate.plusDays(1).atStartOfDay(ZoneId.of("Asia/Kolkata")).toInstant();


        System.err.println(tripRepository.findByPickupPointAndDestinationPointAndPickupTimeBetween(
                pickupPoint, destinationPoint, startOfDay, endOfDay));

        Trip trip = tripRepository.findByPickupPointAndDestinationPointAndPickupTimeBetween(
                pickupPoint, destinationPoint, startOfDay, endOfDay
        );

        return trip;
    }

    public Trip findTrip(String pickupPoint, String destinationPoint, Instant startOfDay, Instant endOfDay) {
        return tripRepository.findByPickupPointAndDestinationPointAndPickupTimeBetween(
                pickupPoint, destinationPoint, startOfDay, endOfDay
        );
    }
}



