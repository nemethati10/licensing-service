package com.thoughtmechanix.organization.controller;

import com.thoughtmechanix.organization.model.Organization;
import com.thoughtmechanix.organization.service.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "v1/organizations")
public class OrganizationServiceController {

    private static final Logger logger = LoggerFactory.getLogger(OrganizationServiceController.class);

    private OrganizationService organizationService;

    public OrganizationServiceController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping(value = "/{organizationId}")
    public Organization getOrganization(@PathVariable("organizationId") String organizationId) {
        Organization organization = organizationService.getOrg(organizationId);
        organization.setContactName(organization.getContactName());

        return organization;
    }

    @PutMapping(value = "/{organizationId}")
    public void updateOrganization(@PathVariable("organizationId") String orgId, @RequestBody Organization org) {
        organizationService.updateOrg(org);
    }

    @PostMapping(value = "/{organizationId}")
    public void saveOrganization(@RequestBody Organization org) {
        organizationService.saveOrg(org);
    }

    @DeleteMapping(value = "/{organizationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization(@PathVariable("orgId") String orgId) {
        organizationService.deleteOrg(orgId);
    }
}