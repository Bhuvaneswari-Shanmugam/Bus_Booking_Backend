package com.app.booking_system.dto;

import com.app.booking_system.entity.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDTO  {

    private Seat seat;
    private Bus bus;
    private Trip trip;
    private UserCredential customer;
    private Date bookingDateTime;
    private String bookingStatus;
    private List<Long> selectedSeats;
    private Long perSeatAmount;
    private Long totalPrice;


}
