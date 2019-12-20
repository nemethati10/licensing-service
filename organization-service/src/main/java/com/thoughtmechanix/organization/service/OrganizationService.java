package com.thoughtmechanix.organization.service;

import com.thoughtmechanix.organization.model.Organization;
import com.thoughtmechanix.organization.repository.OrganizationRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrganizationService {

    private OrganizationRepository organizationRepository;

    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public Organization getOrg(String organizationId) {
        return organizationRepository.findById(organizationId);
    }

    public void saveOrg(Organization org) {
        org.setId(UUID.randomUUID().toString());

        organizationRepository.save(org);

    }

    public void updateOrg(Organization org) {
        organizationRepository.save(org);
    }

    public void deleteOrg(Organization org) {
        organizationRepository.delete(org.getId());
    }
}
