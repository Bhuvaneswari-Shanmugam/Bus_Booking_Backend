package com.app.bookingsystem.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrganizationDTO {

    private String name;
    private Long phoneNumber;
    private String email;
    private String address;
    private String city;
    private String state;
    private Boolean is_active;
}
