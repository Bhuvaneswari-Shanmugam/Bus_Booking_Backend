package com.app.bookingsystem.controller;

import com.app.bookingsystem.dto.ResponseDTO;
import com.app.bookingsystem.entity.Organization;
import com.app.bookingsystem.service.OrganizationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

    private final OrganizationService organizationService;
    public OrganizationController(OrganizationService organizationService){
        this.organizationService=organizationService;
    }

    @PostMapping("/create-organization")
    public ResponseDTO createOrganization(@RequestBody final Organization organization) {
        return this.organizationService.createOrganization(organization);
    }

    @GetMapping("/retrieve-organization")
    public ResponseDTO retrieveAllOrganization(){
        return this.organizationService.retrieveAllOrganization();
    }
}
