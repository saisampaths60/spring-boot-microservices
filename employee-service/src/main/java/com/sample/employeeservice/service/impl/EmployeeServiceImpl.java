package com.sample.employeeservice.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sample.employeeservice.dto.APIResponseDTO;
import com.sample.employeeservice.dto.DepartmentDto;
import com.sample.employeeservice.dto.EmployeeDto;
import com.sample.employeeservice.entity.Employee;
import com.sample.employeeservice.repository.EmployeeRepository;
import com.sample.employeeservice.service.EmployeeService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeRepository employeeRepository;

	private RestTemplate restTemplate;

	@Override
	public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
		Employee employee = new Employee(employeeDto.getId(), employeeDto.getFirstName(), employeeDto.getLastName(),
				employeeDto.getEmail(), employeeDto.getDepartmentCode());
		Employee savedEmployee = employeeRepository.save(employee);
		EmployeeDto savedEmployeeDto = new EmployeeDto(savedEmployee.getId(), savedEmployee.getFirstName(),
				savedEmployee.getLastName(), savedEmployee.getEmail(), savedEmployee.getDepartmentCode());
		return savedEmployeeDto;
	}

	@Override
	public APIResponseDTO getEmployeeById(Long employeeId) {
		Employee employee = employeeRepository.findById(employeeId).get();
		ResponseEntity<DepartmentDto> responseEntity = restTemplate.getForEntity(
				"http://localhost:8080/api/departments/" + employee.getDepartmentCode(), DepartmentDto.class);
		DepartmentDto departmentDto = responseEntity.getBody();
		EmployeeDto employeeDto = new EmployeeDto(employee.getId(), employee.getFirstName(), employee.getLastName(),
				employee.getEmail(), employee.getDepartmentCode());
		APIResponseDTO apiResponseDTO = new APIResponseDTO();
		apiResponseDTO.setDepartmentDto(departmentDto);
		apiResponseDTO.setEmployeeDto(employeeDto);
		return apiResponseDTO;
	}

}
