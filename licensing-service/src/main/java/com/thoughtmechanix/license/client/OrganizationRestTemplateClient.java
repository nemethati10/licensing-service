package com.thoughtmechanix.license.client;

import com.thoughtmechanix.license.model.Organization;
import com.thoughtmechanix.license.util.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrganizationRestTemplateClient {

    private RestTemplate restTemplate;

    public OrganizationRestTemplateClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
// Calling the service directly
//    public Organization getOrganization(String organizationId) {
//        ResponseEntity<Organization> restExchange =
//                restTemplate.exchange(
//                        "http://organizationservice/v1/organizations/{organizationId}",
//                        HttpMethod.GET,
//                        null, Organization.class, organizationId);
//
//        return restExchange.getBody();
//    }

    private static final Logger logger = LoggerFactory.getLogger(OrganizationRestTemplateClient.class);

    /**
     * Calling the service through Zuul Gateway
     *
     * @param organizationId
     * @return
     */
    public Organization getOrganization(String organizationId) {
        logger.debug(">>> In Licensing Service.getOrganization: {}. Thread Id: {}",
                UserContextHolder.getContext().getCorrelationId(), Thread.currentThread().getId());
        ResponseEntity<Organization> restExchange =
                restTemplate.exchange(
                        "http://zuulservice/api/organization/v1/organizations/{organizationId}",
                        HttpMethod.GET, null, Organization.class, organizationId);

        return restExchange.getBody();
    }
}
