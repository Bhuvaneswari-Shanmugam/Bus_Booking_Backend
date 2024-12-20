package com.app.booking_system.service;

import com.app.booking_system.config.UserAuthHelper;
import com.app.booking_system.dto.ResponseDTO;
import com.app.booking_system.entity.Bus;
import com.app.booking_system.entity.Seat;
import com.app.booking_system.exception.badRequestServiceAlartException;
import com.app.booking_system.repository.BusRepository;
import com.app.booking_system.repository.SeatRepository;
import com.app.booking_system.util.Constants;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SeatService {

    private final SeatRepository seatRepository;
    private final BusRepository busRepository;
    public SeatService(SeatRepository seatRepository , BusRepository busRepository){
        this.seatRepository=seatRepository;
        this.busRepository=busRepository;
    }

    public ResponseDTO createSeat(Seat seat){

         Bus bus= busRepository.findById(seat.getBus().getId())
                 .orElseThrow(() -> new badRequestServiceAlartException("Bus Id doesn't exist"));
         Seat obj= Seat.builder()
                 .number(seat.getNumber())
                 .bus(seat.getBus())
                 .build();

             return ResponseDTO.builder()
                     .message(Constants.CREATED)
                     .data(this.seatRepository.save(obj))
                     .statusCode(200)
                     .build();


    }

    public ResponseDTO getAllSeatDetail(){
        return ResponseDTO.builder()
                .message(Constants.CREATED)
                .data(this.seatRepository.findAll())
                .statusCode(200)
                .build();
    }
}
