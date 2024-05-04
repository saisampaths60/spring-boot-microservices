package com.sample.organizationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sample.organizationservice.entity.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

	Organization findByOrganizationCode(String organizationCode);

}
