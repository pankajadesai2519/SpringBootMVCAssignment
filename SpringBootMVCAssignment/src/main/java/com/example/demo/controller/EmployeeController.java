package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

import com.example.demo.exception.EmployeeNotFoundException;
import com.example.demo.model.EmployeeEntity;
import com.example.demo.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	
	@Autowired
	private EmployeeService service;
	
	@GetMapping(path="/",produces="application/json")
	public ResponseEntity<List<EmployeeEntity>> getAllEmployees()
	{
		List<EmployeeEntity> list=service.getAllEmployees();
		return new ResponseEntity<List<EmployeeEntity>>(list,HttpStatus.OK);	
	}
	
	@GetMapping(path="/{id}",produces="application/json")
	public ResponseEntity<EmployeeEntity> getEmployeeById(@PathVariable("id") Long id) throws EmployeeNotFoundException
	{
		EmployeeEntity entity=service.getEmployeeById(id);
		return new ResponseEntity<EmployeeEntity>(entity,HttpStatus.OK);
	}
	
	@DeleteMapping(path="/{id}")
	public HttpStatus deleteEmployeeById(@PathVariable("id") Long id) throws EmployeeNotFoundException
	{
		service.deleteStudentById(id);
		return HttpStatus.FORBIDDEN;
	}
	
	@PostMapping(path="/",consumes="application/json",produces="application/json")
	public ResponseEntity<EmployeeEntity> createEmployee(@Validated @RequestBody EmployeeEntity employee)
	{
		EmployeeEntity created=service.createEmployee(employee);
		return new ResponseEntity<EmployeeEntity>(created,HttpStatus.CREATED);	
	}
	
	@PutMapping(path="/{id}",consumes="application/json",produces="application/json")
	public ResponseEntity<EmployeeEntity> updateEmployee(@Validated @RequestBody EmployeeEntity employee) throws EmployeeNotFoundException
	{
		EmployeeEntity updated=service.updateEmployee(employee);
		return new ResponseEntity<EmployeeEntity>(updated,HttpStatus.OK);
	}
}
