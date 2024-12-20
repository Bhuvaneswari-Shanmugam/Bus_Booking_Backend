package com.app.bookingsystem.dto;

import com.app.bookingsystem.entity.Trip;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusDTO  {
    private Long number;
    private Long tripNumber;
    private Long capacity;
    private String type;



}
