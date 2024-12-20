package com.app.bookingsystem.service;

import com.app.bookingsystem.dto.ResponseDTO;
import com.app.bookingsystem.entity.Bus;
import com.app.bookingsystem.entity.Seat;
import com.app.bookingsystem.repository.BusRepository;
import com.app.bookingsystem.repository.SeatRepository;
import com.app.bookingsystem.util.Constants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class SeatService {

    private final SeatRepository seatRepository;
    private final BusRepository busRepository;
    public SeatService(SeatRepository seatRepository ,BusRepository busRepository){
        this.seatRepository=seatRepository;
        this.busRepository=busRepository;
    }

    public ResponseDTO createSeat(final Long busNumber,final Long seatNumber){

        final Bus existBus=busRepository.findByNumber(busNumber);
         if(existBus != null){
             final Seat obj=Seat.builder()
                     .busNumber(busNumber)
                     .seatNumber(seatNumber)
                     .build();
             return ResponseDTO.builder()
                     .message(Constants.CREATED)
                     .data(this.seatRepository.save(obj))
                     .statusCode(200)
                     .build();
         }
        return ResponseDTO.builder()
                .message("can't create seat for this bus id")
                .statusCode(200)
                .build();
    }

    public ResponseDTO retrieveAllSeatDetails(int page, int size) {
        Page<Seat> userPage = seatRepository.findAll(PageRequest.of(page, size));
        return ResponseDTO.builder()
                .message(Constants.RETRIEVED)
                .data(userPage.getContent())
                .statusCode(200)
                .build();
    }

}
