package com.sample.organizationservice.service.impl;

import org.springframework.stereotype.Service;

import com.sample.organizationservice.dto.OrganizationDto;
import com.sample.organizationservice.entity.Organization;
import com.sample.organizationservice.repository.OrganizationRepository;
import com.sample.organizationservice.service.OrganizationService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

	private OrganizationRepository organizationRepository;

	@Override
	public OrganizationDto saveOrganization(OrganizationDto organizationDto) {
		Organization organization = new Organization(organizationDto.getId(), organizationDto.getOrganizationName(),
				organizationDto.getOrganizationDescription(), organizationDto.getOrganizationCode());

		Organization savedOrganization = organizationRepository.save(organization);

		OrganizationDto savedOrganizationDto = new OrganizationDto(savedOrganization.getId(),
				savedOrganization.getOrganizationName(), savedOrganization.getOrganizationDescription(),
				savedOrganization.getOrganizationCode());

		return savedOrganizationDto;
	}

	@Override
	public OrganizationDto getOrganizationByCode(String organizationCode) {
		Organization organization = organizationRepository.findByOrganizationCode(organizationCode);

		OrganizationDto organizationDto = new OrganizationDto(organization.getId(), organization.getOrganizationName(),
				organization.getOrganizationDescription(), organization.getOrganizationCode());

		return organizationDto;
	}

}
