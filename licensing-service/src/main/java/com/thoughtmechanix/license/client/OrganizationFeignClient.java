package com.thoughtmechanix.license.client;

import com.thoughtmechanix.license.model.Organization;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("organizationservice")
public interface OrganizationFeignClient {
    @RequestMapping(value = "/v1/organizations/{organizationId}", consumes = "application/json", method = RequestMethod.GET)
    Organization getOrganization(@PathVariable("organizationId") String organizationId);
}

