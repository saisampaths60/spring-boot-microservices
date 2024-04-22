package com.sample.employeeservice.service;

import com.sample.employeeservice.dto.APIResponseDTO;
import com.sample.employeeservice.dto.EmployeeDto;

public interface EmployeeService {

	EmployeeDto saveEmployee(EmployeeDto employeeDto);

	APIResponseDTO getEmployeeById(Long employeeId);

}
