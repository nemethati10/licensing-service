package com.thoughtmechanix.license.service;

import com.thoughtmechanix.license.client.OrganizationFeignClient;
import com.thoughtmechanix.license.client.OrganizationRestTemplateClient;
import com.thoughtmechanix.license.config.ServiceConfig;
import com.thoughtmechanix.license.model.License;
import com.thoughtmechanix.license.model.Organization;
import com.thoughtmechanix.license.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LicenseService {

    private LicenseRepository licenseRepository;

    private ServiceConfig serviceConfig;

    @Autowired
    private OrganizationRestTemplateClient organizationRestClient;

    private OrganizationFeignClient organizationFeignClient;

    public LicenseService(LicenseRepository licenseRepository, ServiceConfig serviceConfig, OrganizationFeignClient organizationFeignClient) {
        this.licenseRepository = licenseRepository;
        this.serviceConfig = serviceConfig;
        this.organizationFeignClient = organizationFeignClient;
    }

    public License getLicense(String licenseId) {
        return new License()
                .withId(licenseId)
                .withOrganizationId(UUID.randomUUID().toString())
                .withProductName("Test Product Name")
                .withLicenseType("PerSeat");
    }

    public License getLicense(String organizationId, String licenseId) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        return license.withComment(serviceConfig.getExampleProperty());
    }

    public List<License> getLicensesByOrg(String organizationId) {
        return licenseRepository.findByOrganizationId(organizationId);
    }

    public void saveLicense(License license) {
        license.withId(UUID.randomUUID().toString());
        licenseRepository.save(license);
    }

    public void updateLicense(License license) {
        licenseRepository.save(license);
    }

    public void deleteLicense(License license) {
        licenseRepository.delete(license);
    }

    public License getLicense(String organizationId, String licenseId, String clientType) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);

        Organization org = retrieveOrgInfo(organizationId, clientType);

        return license
                .withOrganizationName(org.getName())
                .withContactName(org.getContactName())
                .withContactEmail(org.getContactEmail())
                .withContactPhone(org.getContactPhone())
                .withComment(serviceConfig.getExampleProperty());
    }

    private Organization retrieveOrgInfo(String organizationId, final String clientType) {
        Organization organization = null;

        switch (clientType) {
            case "rest":
                organization = organizationFeignClient.getOrganization(organizationId);
                break;
            case "feign":
                organization = organizationRestClient.getOrganization(organizationId);
                break;
            default:
                organization = organizationRestClient.getOrganization(organizationId);
        }

        return organization;
    }
}
