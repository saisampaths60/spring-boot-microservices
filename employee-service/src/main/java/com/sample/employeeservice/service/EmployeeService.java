package com.sample.employeeservice.service;

import com.sample.employeeservice.dto.EmployeeDto;

public interface EmployeeService {

	EmployeeDto saveEmployee(EmployeeDto employeeDto);

	EmployeeDto getEmployeeById(Long employeeId);

}
