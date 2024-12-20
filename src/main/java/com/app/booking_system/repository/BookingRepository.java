package com.app.booking_system.repository;

import com.app.booking_system.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,String> {
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.bus.id = :busId")
    Long countBookedSeatsByBusId(@Param("busId") String busId);
    

    Booking findByUserIdAndBusIdAndBookedNoOfSeatsIn(String userId, String busId, List<Long> bookedNoOfSeats);

    List<Booking> findAllByUserId(String id);


    boolean existsByBusIdAndTripIdAndBookedNoOfSeatsIn(String busId, String tripId, List<Long> seatNumbers);

    List<Booking> findByBusId(String id);
}
