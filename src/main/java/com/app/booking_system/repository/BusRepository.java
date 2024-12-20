package com.app.booking_system.repository;

import com.app.booking_system.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRepository extends JpaRepository<Bus,String> {

    Bus findByNumberAndType(Long number, String type);

    Bus findByNumber(Long number);
}
