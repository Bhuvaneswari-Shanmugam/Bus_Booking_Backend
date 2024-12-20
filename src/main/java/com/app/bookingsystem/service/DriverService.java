package com.app.bookingsystem.service;

import com.app.bookingsystem.dto.DriverDTO;
import com.app.bookingsystem.dto.ResponseDTO;
import com.app.bookingsystem.entity.Driver;
import com.app.bookingsystem.entity.UserCredential;
import com.app.bookingsystem.exception.BadRequestServiceAlertException;
import com.app.bookingsystem.repository.DriverRepository;
import com.app.bookingsystem.repository.UserCredentialRepository;
import com.app.bookingsystem.util.Constants;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DriverService {

    private final DriverRepository driverRepository;
    private final UserCredentialRepository userCredentialRepository;
    public DriverService(DriverRepository driverRepository ,UserCredentialRepository userCredentialRepository){
        this.driverRepository=driverRepository;
        this.userCredentialRepository=userCredentialRepository;
    }

    public ResponseDTO createDriver(final DriverDTO driverDTO){
        final UserCredential existUser=userCredentialRepository.findByEmail(driverDTO.getEmail())
                .orElseThrow(()->  new UsernameNotFoundException("user doesn't exist , so please signup"));
        if(!existUser.getEmail().equals(driverDTO.getEmail())){
            throw new BadRequestServiceAlertException("Given mailId and entered mailId should be same");
        }
        Driver driver= Driver.builder()
                .name(driverDTO.getName())
                .email(driverDTO.getEmail())
                .licenseNumber(driverDTO.getLicenseNumber())
                .phoneNumber(driverDTO.getPhoneNumber())
                .trip(driverDTO.getTrip())
                .build();
        return ResponseDTO.builder()
                .message(Constants.CREATED)
                .data(this.driverRepository.save(driver))
                .statusCode(200)
                .build();
    }

    public ResponseDTO retrieveAllDriverDetails(){
        return ResponseDTO.builder()
                .message(Constants.RETRIEVED)
                .data(this.driverRepository.findAll())
                .statusCode(200)
                .build();
    }
}
