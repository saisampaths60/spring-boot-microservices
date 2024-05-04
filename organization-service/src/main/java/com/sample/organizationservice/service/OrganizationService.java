package com.sample.organizationservice.service;

import com.sample.organizationservice.dto.OrganizationDto;

public interface OrganizationService {

	OrganizationDto saveOrganization(OrganizationDto organizationDto);

	OrganizationDto getOrganizationByCode(String organizationCode);

}
