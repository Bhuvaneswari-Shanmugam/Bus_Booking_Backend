package com.app.bookingsystem.service;

import com.app.bookingsystem.dto.ResponseDTO;
import com.app.bookingsystem.entity.Organization;
import com.app.bookingsystem.repository.OrganizationRepository;
import com.app.bookingsystem.util.Constants;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService {

    private final  OrganizationRepository organizationRepository;
    public OrganizationService(OrganizationRepository organizationRepository){
        this.organizationRepository=organizationRepository;
    }

    public ResponseDTO createOrganization(final Organization organization) {
        return ResponseDTO.builder()
                .message(Constants.CREATED)
                .data(this.organizationRepository.save(organization))
                .statusCode(200)
                .build();
    }

    public ResponseDTO retrieveAllOrganization(){
        return ResponseDTO.builder()
                .message(Constants.RETRIEVED)
                .data(this.organizationRepository.findAll())
                .statusCode(200)
                .build();
    }
}
