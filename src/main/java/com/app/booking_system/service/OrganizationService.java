package com.app.booking_system.service;

import com.app.booking_system.dto.ResponseDTO;
import com.app.booking_system.entity.Organization;
import com.app.booking_system.repository.OrganizationRepository;
import com.app.booking_system.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService {


    private final  OrganizationRepository organizationRepository;
    public OrganizationService(OrganizationRepository organizationRepository){
        this.organizationRepository=organizationRepository;
    }

    public ResponseDTO createOrganization(Organization organization) {
        return ResponseDTO.builder()
                .message(Constants.CREATED)
                .data(this.organizationRepository.save(organization))
                .statusCode(200)
                .build();

    }

    public ResponseDTO getAllOrganization(){
        return ResponseDTO.builder()
                .message(Constants.RETRIEVED)
                .data(this.organizationRepository.findAll())
                .statusCode(200)
                .build();
    }
}
