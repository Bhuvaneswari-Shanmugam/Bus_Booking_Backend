package com.app.booking_system.service;

import com.app.booking_system.config.UserAuthHelper;
import com.app.booking_system.dto.DriverDTO;
import com.app.booking_system.dto.ResponseDTO;
import com.app.booking_system.entity.Driver;
import com.app.booking_system.entity.UserCredential;
import com.app.booking_system.exception.badRequestServiceAlartException;
import com.app.booking_system.repository.DriverRepository;
import com.app.booking_system.repository.UserCredentialRepository;
import com.app.booking_system.util.Constants;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DriverService {

    private final DriverRepository driverRepository;
    private final UserCredentialRepository userCredentialRepository;
    public DriverService(DriverRepository driverRepository , UserCredentialRepository userCredentialRepository){
        this.driverRepository=driverRepository;
        this.userCredentialRepository=userCredentialRepository;
    }

    public ResponseDTO createDriver(DriverDTO driverDto){

        UserCredential existUser=userCredentialRepository.findByEmail(driverDto.getEmail())
                .orElseThrow(()->  new UsernameNotFoundException("user doesn't exist , so please signup"));
        if(!existUser.getEmail().equals(driverDto.getEmail())){
            throw new badRequestServiceAlartException("Given mailId and entered mailId should be same");
        }
        Driver driver= Driver.builder()
                .name(driverDto.getName())
                .email(driverDto.getEmail())
                .licenseNumber(driverDto.getLicenseNumber())
                .phoneNumber(driverDto.getPhoneNumber())
                .trip(driverDto.getTrip())
                .build();
        return ResponseDTO.builder()
                .message(Constants.CREATED)
                .data(this.driverRepository.save(driver))
                .statusCode(200)
                .build();
    }

    public ResponseDTO getAllDriverDetail(){
        return ResponseDTO.builder()
                .message(Constants.RETRIEVED)
                .data(this.driverRepository.findAll())
                .statusCode(200)
                .build();
    }
}
