package com.thoughtmechanix.license.controller;

import com.thoughtmechanix.license.config.ServiceConfig;
import com.thoughtmechanix.license.model.License;
import com.thoughtmechanix.license.service.LicenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {

    private static final Logger logger = LoggerFactory.getLogger(LicenseServiceController.class);

    private LicenseService licenseService;
    private ServiceConfig serviceConfig;

    @Autowired
    private HttpServletRequest request;

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

        logger.debug("Found tmx-correlation-id in license-service-controller: {} ",
                request.getHeader("tmx-correlation-id"));
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

    @GetMapping(value = "/{licenseId}/{clientType}")
    public License getLicensesWithClient(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("licenseId") String licenseId,
            @PathVariable("clientType") String clientType) {

        return licenseService.getLicense(organizationId,
                licenseId, clientType);
    }
}