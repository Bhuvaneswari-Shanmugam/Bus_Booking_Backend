package com.app.bookingsystem.service;

import com.app.bookingsystem.dto.ResponseDTO;
import com.app.bookingsystem.dto.TripDTO;
import com.app.bookingsystem.entity.Trip;
import com.app.bookingsystem.entity.UserCredential;
import com.app.bookingsystem.repository.TripRepository;
import com.app.bookingsystem.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class TripService {

    private final TripRepository tripRepository;
    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;

    }

    public ResponseDTO retrieveAllTrip(int page, int size) {
        Page<Trip> userPage = this.tripRepository.findAll(PageRequest.of(page, size));
        return ResponseDTO.builder()
                .message(Constants.RETRIEVED)
                .data(userPage.getContent())
                .statusCode(200)
                .build();
    }

    public boolean existsTrip(String pickupPoint, String destinationPoint, String pickupTime) {

        final LocalDate pickupDate = LocalDate.parse(pickupTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        final Instant startOfDay = pickupDate.atStartOfDay(ZoneId.of("Asia/Kolkata")).toInstant();
        final Instant endOfDay = pickupDate.plusDays(1).atStartOfDay(ZoneId.of("Asia/Kolkata")).toInstant();
        return tripRepository.existsByPickupPointAndDestinationPointAndPickupTimeBetween(
                pickupPoint, destinationPoint, startOfDay, endOfDay
        );
    }

    public Trip findTrip(String pickupPoint, String destinationPoint, String pickupTime) {

        final LocalDate pickupDate = LocalDate.parse(pickupTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        final Instant startOfDay = pickupDate.atStartOfDay(ZoneId.of("Asia/Kolkata")).toInstant();
        final Instant endOfDay = pickupDate.plusDays(1).atStartOfDay(ZoneId.of("Asia/Kolkata")).toInstant();
        return tripRepository.findByPickupPointAndDestinationPointAndPickupTimeBetween(
                pickupPoint, destinationPoint, startOfDay, endOfDay);
    }

    public Trip findTrip(String pickupPoint, String destinationPoint, Instant startOfDay, Instant endOfDay) {
        return tripRepository.findByPickupPointAndDestinationPointAndPickupTimeBetween(
                pickupPoint, destinationPoint, startOfDay, endOfDay
        );
    }

    public ResponseDTO createTrip(TripDTO tripDTO) {
        Trip savedTrip = Trip.builder()
                .tripNumber(tripDTO.getTripNumber())
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


    @Transactional
    public ResponseDTO updateTrip(final String id,final TripDTO tripDto) {

            Trip existingTrip=this.tripRepository.findById(id).orElseThrow(() -> new RuntimeException("Trip not found"));
            if(existingTrip !=null){
                if(tripDto.getTripNumber() != null){
                    existingTrip.setTripNumber(tripDto.getTripNumber());
                }

                if (tripDto.getPickupPoint() != null) {
                    existingTrip.setPickupPoint(tripDto.getPickupPoint());
                }
                if (tripDto.getDestinationPoint() != null) {
                    existingTrip.setDestinationPoint(tripDto.getDestinationPoint());
                }
                if (tripDto.getPickupTime() != null) {
                    existingTrip.setPickupTime(tripDto.getPickupTime());
                }
                if (tripDto.getReachingTime() != null) {
                    existingTrip.setReachingTime(tripDto.getReachingTime());
                }
                if (tripDto.getExpense() != null) {
                    existingTrip.setExpense(tripDto.getExpense());
                }
                final Trip updatedTrip = tripRepository.save(existingTrip);
                return ResponseDTO.builder()
                        .message(Constants.RETRIEVED)
                        .data(updatedTrip)
                        .statusCode(200)
                        .build();
            }
            return ResponseDTO.builder()
                    .message("")
                    .statusCode(500)
                    .build();

    }

    public ResponseDTO retrieveTripById(String id) {
         Optional<Trip> trip=this.tripRepository.findById(id);
         return  ResponseDTO.builder()
                 .message(Constants.RETRIEVED)
                 .data(trip)
                 .statusCode(200)
                 .build();

    }
}
