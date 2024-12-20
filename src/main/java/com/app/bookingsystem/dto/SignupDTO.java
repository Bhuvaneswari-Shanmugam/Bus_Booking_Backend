package com.app.bookingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupDTO {


    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private Boolean termsAccepted;
}
