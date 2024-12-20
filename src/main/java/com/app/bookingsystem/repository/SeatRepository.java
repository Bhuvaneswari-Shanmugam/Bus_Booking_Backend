package com.app.bookingsystem.repository;

import com.app.bookingsystem.entity.Bus;
import com.app.bookingsystem.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat,String> {

}
