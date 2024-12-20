package com.app.booking_system.dto;

import com.app.booking_system.entity.Trip;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusDTO  {


    private Long number;
    private String name;
    private Long capacity;
    private String type;
    private Trip trip;


}
