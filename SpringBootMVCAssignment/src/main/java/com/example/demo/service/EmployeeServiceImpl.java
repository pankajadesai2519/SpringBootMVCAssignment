package com.example.demo.service;


import java.util.List;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.example.demo.exception.EmployeeNotFoundException;
import com.example.demo.model.EmployeeEntity;
import com.example.demo.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Override
	public List<EmployeeEntity> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public Optional<EmployeeEntity> getEmployeeById(int id) {
		return employeeRepository.findById(id);
	}
	
	@Override
	public EmployeeEntity createEmployee(EmployeeEntity newEmployee) {
		return employeeRepository.save(newEmployee);
	}
	
	

	@Override
	public EmployeeEntity updateEmployee(int id, EmployeeEntity employeeUpdate) {
		if(id!=employeeUpdate.getId()) {
			throw new HttpClientErrorException(HttpStatus.CONFLICT,"Id in URI does not matchid to update");
		}
		Optional<EmployeeEntity> employee=employeeRepository.findById(id);
		if(!employee.isPresent()) {
			throw new EmployeeNotFoundException("Employee with id("+id+") not found!");
		}
		EmployeeEntity originalEmployee=employee.get();
		BeanUtils.copyProperties(employeeUpdate,originalEmployee);
		return employeeRepository.save(originalEmployee);
	}

	@Override
	public void deleteEmployee(int id) {
		Optional<EmployeeEntity> employee=employeeRepository.findById(id);
		if(!employee.isPresent()) 
		{
			throw new EmployeeNotFoundException("Employee with id("+id+") not found!");
		}
		employeeRepository.delete(employee.get());
		
	}	
}
