package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.example.demo.exception.EmployeeNotFoundException;
import com.example.demo.model.EmployeeEntity;
import com.example.demo.service.EmployeeService;

@RestController
@RequestMapping("/demo")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping(path="/employees",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<EmployeeEntity>> getAllEmployees()
	{
		List<EmployeeEntity> employees=employeeService.getAllEmployees();
		if(employees.isEmpty()) 
		{
			throw new EmployeeNotFoundException("No employee records were found");
		}
		return new ResponseEntity<List<EmployeeEntity>>(employees,HttpStatus.OK);	
	}
	
	@GetMapping(path="/employees/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public EmployeeEntity getEmployeeById(@PathVariable("id") int id)
	{
		Optional<EmployeeEntity> employee=employeeService.getEmployeeById(id);
		
		if(employee.isPresent())
		{
			return employee.get();
		}
		else 
		{
			throw new EmployeeNotFoundException("No employee record exist for given id");
		}
	}
	
	@DeleteMapping(path="/employees/delete/{id}")
	public ResponseEntity<Object> deleteEmployee(@PathVariable("id") int id)
	{
		employeeService.deleteEmployee(id);
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping(path="/employees/create",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeEntity> createEmployee(@Validated @RequestBody EmployeeEntity createEmployee)
	{
		Optional<EmployeeEntity> employee=employeeService.getEmployeeById(createEmployee.getId());
		if(employee.isPresent()) 
		{
			throw new HttpClientErrorException(HttpStatus.CONFLICT, "Employee with id"+"("+createEmployee.getId()+") already exits");
		}
		return new ResponseEntity<EmployeeEntity>(employeeService.createEmployee(createEmployee),HttpStatus.CREATED);	
	}
	
	@PutMapping(path="/employees/update{id}",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeEntity> updateEmployee(@PathVariable int id,@Validated @RequestBody EmployeeEntity employee)
	{
		return new ResponseEntity<EmployeeEntity>(employeeService.updateEmployee(id, employee),HttpStatus.ACCEPTED);
	}
}
