package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.EmployeeEntity;

public interface EmployeeService {

	List<EmployeeEntity> getAllEmployees();
	
	Optional<EmployeeEntity> getEmployeeById(int id);
	
	EmployeeEntity createEmployee(EmployeeEntity newEmployee);
	
	EmployeeEntity updateEmployee(int id,EmployeeEntity employeeUpdate);
	
	void deleteEmployee(int id);
	
	
}
