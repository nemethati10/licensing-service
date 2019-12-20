package com.thoughtmechanix.organization.controller;

import com.thoughtmechanix.organization.model.Organization;
import com.thoughtmechanix.organization.service.OrganizationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "v1/organizations")
public class OrganizationServiceController {

    private OrganizationService organizationService;

    public OrganizationServiceController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }


    @RequestMapping(value = "/{organizationId}", method = RequestMethod.GET)
    public Organization getOrganization(@PathVariable("organizationId") String organizationId) {
        return organizationService.getOrg(organizationId);
    }

    @RequestMapping(value = "/{organizationId}", method = RequestMethod.PUT)
    public void updateOrganization(@PathVariable("organizationId") String orgId, @RequestBody Organization org) {
        organizationService.updateOrg(org);
    }

    @RequestMapping(value = "/{organizationId}", method = RequestMethod.POST)
    public void saveOrganization(@RequestBody Organization org) {
        organizationService.saveOrg(org);
    }

    @RequestMapping(value = "/{organizationId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization(@PathVariable("orgId") String orgId, @RequestBody Organization org) {
        organizationService.deleteOrg(org);
    }
}