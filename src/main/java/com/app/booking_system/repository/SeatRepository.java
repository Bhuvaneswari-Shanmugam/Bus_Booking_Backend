package com.app.booking_system.repository;

import com.app.booking_system.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat,String> {
    Seat findByNumberAndBusId(Long seatNumber, String busId);


}
