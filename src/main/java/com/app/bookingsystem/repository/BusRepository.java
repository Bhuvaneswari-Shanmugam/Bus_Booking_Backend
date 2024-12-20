package com.app.bookingsystem.repository;

import com.app.bookingsystem.entity.Bus;
import com.app.bookingsystem.entity.UserCredential;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusRepository extends JpaRepository<Bus,String> {

    Bus findByNumberAndType(Long number, String type);
    Bus findByNumber(Long number);

}
