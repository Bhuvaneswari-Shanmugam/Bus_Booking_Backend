package com.app.bookingsystem.controller;

import com.app.bookingsystem.dto.ResponseDTO;
import com.app.bookingsystem.service.AdminService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController( AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/retrieve-admin-detail")
    public ResponseDTO retrieveAdminDetails() {
        return this.adminService.retrieveAdminDetails();
    }
}
