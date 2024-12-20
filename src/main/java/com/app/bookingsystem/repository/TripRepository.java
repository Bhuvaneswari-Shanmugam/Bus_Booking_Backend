package com.app.bookingsystem.repository;

import com.app.bookingsystem.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.Instant;


@Repository
public interface TripRepository extends JpaRepository<Trip,String> {
    boolean existsByPickupPointAndDestinationPointAndPickupTimeBetween(String pickupPoint, String destinationPoint, Instant startOfDay, Instant endOfDay);
    Trip findByPickupPointAndDestinationPointAndPickupTimeBetween(String pickupPoint, String destinationPoint, Instant startOfDay, Instant endOfDay);
}
