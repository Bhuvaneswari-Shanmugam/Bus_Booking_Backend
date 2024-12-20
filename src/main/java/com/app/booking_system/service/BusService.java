package com.app.booking_system.service;

import com.app.booking_system.dto.BusDTO;
import com.app.booking_system.dto.ResponseDTO;
import com.app.booking_system.entity.Bus;
import com.app.booking_system.exception.badRequestServiceAlartException;
import com.app.booking_system.repository.BookingRepository;
import com.app.booking_system.repository.BusRepository;
import com.app.booking_system.util.Constants;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BusService {

    private final BusRepository busRepository;
    private final BookingRepository bookingRepository;


    public BusService(BusRepository busRepository , BookingRepository bookingRepository){
        this.busRepository=busRepository;
        this.bookingRepository=bookingRepository;

    }

    public ResponseDTO createBus(BusDTO busDTO){

        Bus bus= Bus.builder()
                .number(busDTO.getNumber())
                .capacity(busDTO.getCapacity())
                .type(busDTO.getType())
                .trip(busDTO.getTrip())
                .build();
        return ResponseDTO.builder()
                .message(Constants.CREATED)
                .data(this.busRepository.save(bus))
                .statusCode(200)
                .build();

    }

    public ResponseDTO getAllBusDetails(){
        List<Bus> buses = this.busRepository.findAll();

        List<Map<String, Object>> busDataWithSeats = buses.stream().map(bus -> {
            Map<String, Object> busData = new HashMap<>();
            busData.put("id", bus.getId());
            busData.put("number", bus.getNumber());
            busData.put("capacity", bus.getCapacity());
            busData.put("type", bus.getType());
            busData.put("trip", bus.getTrip());
            busData.put("createdAt", bus.getCreatedAt());
            busData.put("updatedAt", bus.getUpdatedAt());

            Long availableSeats = this.getAvailableSeatCount(bus.getNumber(), bus.getType());
            busData.put("availableSeats", availableSeats);

            return busData;
        }).collect(Collectors.toList());

        return ResponseDTO.builder()
                .message(Constants.RETRIEVED)
                .data(busDataWithSeats)
                .statusCode(200)
                .build();
    }


    public ResponseDTO getBusById(@PathVariable String id){
       Bus bus=busRepository.findById(id).orElseThrow(()-> new badRequestServiceAlartException("Bus doesn't exist"));
       return ResponseDTO.builder()
               .statusCode(200)
               .data(bus)
               .message(Constants.RETRIEVED)
               .build();
    }


    public ResponseDTO updateBus(String id, BusDTO busDTO) {
        Bus bus=busRepository.findById(id).orElseThrow(()->new badRequestServiceAlartException("Bus does not exist"));
        if (busDTO.getNumber() != null) {
            bus.setNumber(busDTO.getNumber());
        }
        if (busDTO.getCapacity() != null) {
            bus.setCapacity(busDTO.getCapacity());
        }
        if (busDTO.getType() != null) {
            bus.setType(busDTO.getType());
        }
        if (busDTO.getTrip() != null) {
            bus.setTrip(busDTO.getTrip());
        }

        busRepository.save(bus);
        return ResponseDTO.builder()
                .message(Constants.UPDATED)
                .data(bus)
                .statusCode(200)
                .build();
    }

    public Long getAvailableSeatCount(Long busNumber, String type) {
        Bus bus = busRepository.findByNumberAndType(busNumber, type);
        if (bus == null) {
            throw new badRequestServiceAlartException("Bus with specified number and type does not exist");
        }
        String busId = bus.getId();
        Long bookedSeats = bookingRepository.countBookedSeatsByBusId(busId);
        return bus.getCapacity() - bookedSeats;
    }

    public Bus findBusId(Long busNumber, String busType) {
        Bus bus = busRepository.findByNumberAndType(busNumber, busType);
        return bus;
    }
}
