package com.sample.employeeservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sample.employeeservice.dto.OrganizationDto;

@FeignClient(name = "ORGANIZATION-SERVICE")
public interface APIClientOrganizationService {

	@GetMapping("api/organizations/{organization-code}")
	OrganizationDto getOrganization(@PathVariable("organization-code") String organizationCode);

}
