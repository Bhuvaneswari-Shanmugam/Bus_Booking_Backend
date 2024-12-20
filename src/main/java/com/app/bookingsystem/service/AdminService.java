package com.app.bookingsystem.service;

import com.app.bookingsystem.dto.ResponseDTO;
import com.app.bookingsystem.repository.AdminRepository;
import com.app.bookingsystem.util.Constants;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public ResponseDTO retrieveAdminDetails() {
        return ResponseDTO.builder()
                .message(Constants.RETRIEVED)
                .data(this.adminRepository.findAll())
                .statusCode(200)
                .build();
    }
}


