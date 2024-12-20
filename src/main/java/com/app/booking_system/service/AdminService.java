package com.app.booking_system.service;

import com.app.booking_system.dto.ResponseDTO;
import com.app.booking_system.repository.AdminRepository;
import com.app.booking_system.repository.OrganizationRepository;
import com.app.booking_system.repository.UserCredentialRepository;
import com.app.booking_system.util.Constants;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final OrganizationRepository organizationRepository;
    private final UserCredentialRepository userCredentialRepository;


    public AdminService(AdminRepository adminRepository, OrganizationRepository organizationRepository, UserCredentialRepository userCredentialRepository) {
        this.adminRepository = adminRepository;
        this.organizationRepository = organizationRepository;
        this.userCredentialRepository = userCredentialRepository;

    }



        public ResponseDTO getAllAdminDetails () {
            return ResponseDTO.builder()
                    .message(Constants.RETRIEVED)
                    .data(this.adminRepository.findAll())
                    .statusCode(200)
                    .build();
        }



}


