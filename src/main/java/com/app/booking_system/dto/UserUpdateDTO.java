package com.app.booking_system.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO
{

    private String email;
    private String address;
    private String contactNumber;
    private String gender;
    private LocalDate dateOfBirth;



}
