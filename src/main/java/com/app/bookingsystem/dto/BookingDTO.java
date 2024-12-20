package com.app.bookingsystem.dto;

import com.app.bookingsystem.entity.*;
import lombok.*;

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
