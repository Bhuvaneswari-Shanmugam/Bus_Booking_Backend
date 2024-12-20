package com.app.booking_system.controller;

import com.app.booking_system.dto.ResponseDTO;
import com.app.booking_system.entity.Organization;
import com.app.booking_system.service.OrganizationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

    private final OrganizationService organizationService;
    public OrganizationController(OrganizationService organizationService){
        this.organizationService=organizationService;
    }

    @PostMapping("/create")
    public ResponseDTO createOrganization(@RequestBody  Organization organization) {
        return this.organizationService.createOrganization(organization);
    }

    @GetMapping("/fetch")
    public ResponseDTO getAllOrganization(){
        return this.organizationService.getAllOrganization();
    }
}
