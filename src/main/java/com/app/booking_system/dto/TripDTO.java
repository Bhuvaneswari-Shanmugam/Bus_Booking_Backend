package com.app.booking_system.dto;


import lombok.*;

import java.time.Instant;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TripDTO {

    private String pickupPoint;
    private String destinationPoint;
    private Instant pickupTime;
    private Instant reachingTime;
    private Long expense;
//    private Organization organization;
}
