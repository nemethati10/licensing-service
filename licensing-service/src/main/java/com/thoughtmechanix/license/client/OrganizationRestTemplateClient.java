package com.thoughtmechanix.license.client;

import com.thoughtmechanix.license.model.Organization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrganizationRestTemplateClient {

    private static final Logger logger = LoggerFactory.getLogger(OrganizationRestTemplateClient.class);

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Calling the service through Zuul Gateway
     *
     * @param organizationId
     * @return
     */
    public Organization getOrganization(String organizationId) {
        ResponseEntity<Organization> restExchange =
                restTemplate.exchange(
                        "http://zuulservice/api/organization/v1/organizations/{organizationId}",
                        HttpMethod.GET, null, Organization.class, organizationId);

        return restExchange.getBody();
    }
}
