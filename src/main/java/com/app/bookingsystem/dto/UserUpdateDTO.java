package com.app.bookingsystem.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO
{

    private String firstName;
    private String lastName;
    private String email;
    private String password;




}
