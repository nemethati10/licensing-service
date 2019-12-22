package com.thoughtmechanix.license.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.thoughtmechanix.license.client.OrganizationRestTemplateClient;
import com.thoughtmechanix.license.model.License;
import com.thoughtmechanix.license.model.Organization;
import com.thoughtmechanix.license.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class LicenseService {

    private LicenseRepository licenseRepository;

    @Autowired
    private OrganizationRestTemplateClient organizationRestClient;

    public LicenseService(LicenseRepository licenseRepository) {
        this.licenseRepository = licenseRepository;
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
        return license.withComment("");
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
                .withContactPhone(org.getContactPhone());
    }

    @HystrixCommand(fallbackMethod = "buildFallbackLicenseList",
            threadPoolKey = "licenseByOrgThreadPool",
            threadPoolProperties =
                    {@HystrixProperty(name = "coreSize", value = "30"),
                            @HystrixProperty(name = "maxQueueSize", value = "10")},
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "75"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "7000"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "15000"),
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "5")}
    )
    public List<License> getLicensesByOrg(String organizationId) {
        simualateDelay();
        return licenseRepository.findByOrganizationId(organizationId);
    }

    private void simualateDelay() {
        Random rand = new Random();
        int randomNum = rand.nextInt((3 - 1) + 1) + 1;

        if (randomNum == 3) {
            try {
                Thread.sleep(11000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Organization retrieveOrgInfo(String organizationId, final String clientType) {
        Organization organization = null;

        switch (clientType) {
            case "rest":
                organization = organizationRestClient.getOrganization(organizationId);
                break;
            default:
                organization = organizationRestClient.getOrganization(organizationId);
        }

        return organization;
    }

    private List<License> buildFallbackLicenseList(String organizationId) {
        List<License> fallbackList = new ArrayList<>();
        License license = new License()
                .withId("0000000-00-00000")
                .withOrganizationId(organizationId)
                .withProductName("Sorry no licensing information currently available");

        fallbackList.add(license);
        return fallbackList;
    }
}
