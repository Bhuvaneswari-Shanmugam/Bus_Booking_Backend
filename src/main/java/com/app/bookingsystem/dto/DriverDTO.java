package com.app.bookingsystem.dto;


import com.app.bookingsystem.entity.Trip;
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
