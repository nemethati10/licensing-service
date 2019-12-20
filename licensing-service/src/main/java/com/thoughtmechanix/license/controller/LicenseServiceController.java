package com.thoughtmechanix.license.controller;

import com.thoughtmechanix.license.config.ServiceConfig;
import com.thoughtmechanix.license.model.License;
import com.thoughtmechanix.license.service.LicenseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {

    private LicenseService licenseService;
    private ServiceConfig serviceConfig;

    public LicenseServiceController(LicenseService licenseService, ServiceConfig serviceConfig) {
        this.licenseService = licenseService;
        this.serviceConfig = serviceConfig;
    }

    @GetMapping
    public List<License> getLicenses(@PathVariable("organizationId") String organizationId) {
        return licenseService.getLicensesByOrg(organizationId);
    }

    @GetMapping(value = "/{licenseId}")
    public License getLicenses(@PathVariable("organizationId") String organizationId,
                               @PathVariable("licenseId") String licenseId) {

        return licenseService.getLicense(organizationId, licenseId);
    }

    @PutMapping(value = "{licenseId}")
    public String updateLicenses(@PathVariable("licenseId") String licenseId) {
        return String.format("This is the put");
    }

    @PostMapping
    public void saveLicenses(@RequestBody License license) {
        licenseService.saveLicense(license);
    }

    @RequestMapping(value = "{licenseId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteLicenses(@PathVariable("licenseId") String licenseId) {
        return String.format("This is the Delete");
    }

    @GetMapping(value="/{licenseId}/{clientType}")
    public License getLicensesWithClient(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("licenseId") String licenseId,
            @PathVariable("clientType") String clientType) {

        return licenseService.getLicense(organizationId,
                licenseId, clientType);
    }
}