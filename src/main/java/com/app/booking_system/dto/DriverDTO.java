package com.app.booking_system.dto;


import com.app.booking_system.entity.Trip;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DriverDTO  {

    private String name;
    private String email;
    private String licenseNumber;
    private Long phoneNumber;
    private Trip trip;

}
