package com.sample.employeeservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.sample.employeeservice.dto.APIResponseDTO;
import com.sample.employeeservice.dto.DepartmentDto;
import com.sample.employeeservice.dto.EmployeeDto;
import com.sample.employeeservice.dto.OrganizationDto;
import com.sample.employeeservice.entity.Employee;
import com.sample.employeeservice.repository.EmployeeRepository;
import com.sample.employeeservice.service.APIClientDepartmentService;
import com.sample.employeeservice.service.APIClientOrganizationService;
import com.sample.employeeservice.service.EmployeeService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeRepository employeeRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

//	private RestTemplate restTemplate;

//	private WebClient webClient;

	private APIClientDepartmentService apiClient;

	private APIClientOrganizationService apiClientOrganizationService;

	@Override
	public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
		Employee employee = new Employee(employeeDto.getId(), employeeDto.getFirstName(), employeeDto.getLastName(),
				employeeDto.getEmail(), employeeDto.getDepartmentCode(), employeeDto.getOrganizationCode());
		Employee savedEmployee = employeeRepository.save(employee);
		EmployeeDto savedEmployeeDto = new EmployeeDto(savedEmployee.getId(), savedEmployee.getFirstName(),
				savedEmployee.getLastName(), savedEmployee.getEmail(), savedEmployee.getDepartmentCode(),
				savedEmployee.getOrganizationCode());
		return savedEmployeeDto;
	}

//	@CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartmentAndOrganization")
	@Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartmentAndOrganization")
	@Override
	public APIResponseDTO getEmployeeById(Long employeeId) {
		LOGGER.info("inside getEmployeeById method");
		Employee employee = employeeRepository.findById(employeeId).get();
//		ResponseEntity<DepartmentDto> responseEntity = restTemplate.getForEntity(
//				"http://localhost:8080/api/departments/" + employee.getDepartmentCode(), DepartmentDto.class);
//		DepartmentDto departmentDto = responseEntity.getBody();

//		DepartmentDto departmentDto = webClient.get()
//				.uri("http://localhost:8080/api/departments/" + employee.getDepartmentCode()).retrieve()
//				.bodyToMono(DepartmentDto.class).block();

		DepartmentDto departmentDto = apiClient.getDepartment(employee.getDepartmentCode());

		OrganizationDto organizationDto = apiClientOrganizationService.getOrganization(employee.getOrganizationCode());

		EmployeeDto employeeDto = new EmployeeDto(employee.getId(), employee.getFirstName(), employee.getLastName(),
				employee.getEmail(), employee.getDepartmentCode(), employee.getOrganizationCode());
		APIResponseDTO apiResponseDTO = new APIResponseDTO();
		apiResponseDTO.setDepartmentDto(departmentDto);
		apiResponseDTO.setEmployeeDto(employeeDto);
		apiResponseDTO.setOrganizationDto(organizationDto);
		return apiResponseDTO;
	}

	public APIResponseDTO getDefaultDepartmentAndOrganization(Long employeeId, Exception exception) {
		LOGGER.info("inside getDefaultDepartment method");
		Employee employee = employeeRepository.findById(employeeId).get();

		DepartmentDto departmentDto = new DepartmentDto();
		departmentDto.setDepartmentName("R&D Department");
		departmentDto.setDepartmentCode("RD001");
		departmentDto.setDepartmentDescription("Research and Development Department");

		OrganizationDto organizationDto = new OrganizationDto();
		organizationDto.setOrganizationName("R&D Organization");
		organizationDto.setOrganizationCode("RD001");
		organizationDto.setOrganizationDescription("Research and Development Organization");

		EmployeeDto employeeDto = new EmployeeDto(employee.getId(), employee.getFirstName(), employee.getLastName(),
				employee.getEmail(), employee.getDepartmentCode(), employee.getOrganizationCode());
		APIResponseDTO apiResponseDTO = new APIResponseDTO();
		apiResponseDTO.setDepartmentDto(departmentDto);
		apiResponseDTO.setEmployeeDto(employeeDto);
		return apiResponseDTO;
	}

}
