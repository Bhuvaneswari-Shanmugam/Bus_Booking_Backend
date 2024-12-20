package com.app.booking_system.dto;

public class JwtDto {
    String accessToken;
    String refreshToken;
    public  JwtDto(String accessToken , String refreshToken){
        this.accessToken=accessToken;
        this.refreshToken=refreshToken;
    }
}
