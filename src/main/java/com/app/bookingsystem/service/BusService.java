package com.app.bookingsystem.service;

import com.app.bookingsystem.dto.BusDTO;
import com.app.bookingsystem.dto.ResponseDTO;
import com.app.bookingsystem.entity.Bus;
import com.app.bookingsystem.exception.BadRequestServiceAlertException;
import com.app.bookingsystem.repository.BookingRepository;
import com.app.bookingsystem.repository.BusRepository;
import com.app.bookingsystem.util.Constants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BusService {

    private final BusRepository busRepository;
    private final BookingRepository bookingRepository;
    public BusService(BusRepository busRepository ,BookingRepository bookingRepository){
        this.busRepository=busRepository;
        this.bookingRepository=bookingRepository;
    }

    public ResponseDTO createBus(final BusDTO busDTO){
        final Bus bus= Bus.builder()
                .number(busDTO.getNumber())
                .capacity(busDTO.getCapacity())
                .type(busDTO.getType())
                .tripNumber(busDTO.getTripNumber())
                .build();
        return ResponseDTO.builder()
                .message(Constants.CREATED)
                .data(this.busRepository.save(bus))
                .statusCode(200)
                .build();
    }

    public ResponseDTO retrieveAllBuses(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Bus> busPage = busRepository.findAll(pageable);

        List<Map<String, Object>> busDataWithSeats = busPage.getContent().stream().map(bus -> {
            Map<String, Object> busData = new HashMap<>();
            busData.put("id", bus.getId());
            busData.put("number", bus.getNumber());
            busData.put("capacity", bus.getCapacity());
            busData.put("type", bus.getType());
            busData.put("trip", bus.getTripNumber());
            busData.put("createdAt", bus.getCreatedAt());
            busData.put("updatedAt", bus.getUpdatedAt());

            final Long availableSeats = this.getAvailableSeatCount(bus.getNumber(), bus.getType());
            busData.put("availableSeats", availableSeats);

            return busData;
        }).collect(Collectors.toList());

        return ResponseDTO.builder()
                .message(Constants.RETRIEVED)
                .data(busDataWithSeats)
                .statusCode(200)
                .build();
    }


    public ResponseDTO retrieveBusById(@PathVariable final String id){
       final Bus bus=busRepository.findById(id).orElseThrow(()-> new BadRequestServiceAlertException("Bus doesn't exist"));
       return ResponseDTO.builder()
               .statusCode(200)
               .data(bus)
               .message(Constants.RETRIEVED)
               .build();
    }
    public ResponseDTO updateBus(final String id,final  BusDTO busDTO) {
        final Bus bus=busRepository.findById(id).orElseThrow(()->new BadRequestServiceAlertException("Bus does not exist"));
        if (busDTO.getNumber() != null) {
            bus.setNumber(busDTO.getNumber());
        }
        if (busDTO.getCapacity() != null) {
            bus.setCapacity(busDTO.getCapacity());
        }
        if (busDTO.getType() != null) {
            bus.setType(busDTO.getType());
        }
        if (busDTO.getTripNumber() != null) {
            bus.setTripNumber(busDTO.getTripNumber());
        }

        return ResponseDTO.builder()
                .message(Constants.UPDATED)
                .data(this.busRepository.save(bus))
                .statusCode(200)
                .build();
    }

    public Long getAvailableSeatCount(final Long busNumber,final String type) {
        final Bus bus = busRepository.findByNumberAndType(busNumber, type);
        if (bus == null) {
            throw new BadRequestServiceAlertException("Bus with specified number and type does not exist");
        }
        final String busId = bus.getId();
        final Long bookedSeats = bookingRepository.countBookedSeatsByBusId(busId);
        return bus.getCapacity() - bookedSeats;
    }

    public Bus findBusId(final Long busNumber, final String busType) {
        return busRepository.findByNumberAndType(busNumber, busType);
    }
}
