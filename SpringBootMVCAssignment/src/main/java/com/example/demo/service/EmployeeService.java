package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.EmployeeNotFoundException;
import com.example.demo.model.EmployeeEntity;
import com.example.demo.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	@Autowired
	EmployeeRepository repository;
	
	public List<EmployeeEntity> getAllEmployees(){
		List<EmployeeEntity> employeeList=repository.findAll();
		
		if(employeeList.size()>0) 
		{
			return employeeList;
		}
		else 
		{
			return new ArrayList<EmployeeEntity>();
		}
	}
	
	public EmployeeEntity getEmployeeById(Long id) throws EmployeeNotFoundException
	{
		Optional<EmployeeEntity> employee=repository.findById(id);
		
		if(employee.isPresent())
		{
			return employee.get();
		}
		else 
		{
			throw new EmployeeNotFoundException("No student record exist for given id",id);
		}
	}
	
	public void deleteStudentById(Long id) throws EmployeeNotFoundException
	{
		Optional<EmployeeEntity> employee=repository.findById(id);
		
		if(employee.isPresent()) 
		{
			repository.deleteById(id);
		}
		else 
		{
			throw new EmployeeNotFoundException("No student record exist for given id",id);
		}
	}
	
	public EmployeeEntity createEmployee(EmployeeEntity entity) {
		entity=repository.save(entity);
		return entity;
	}
	
	public EmployeeEntity updateEmployee(EmployeeEntity entity) throws EmployeeNotFoundException{
			Optional<EmployeeEntity> employee=repository.findById(entity.getId());
			
			if(employee.isPresent()) 
			{
				EmployeeEntity newEntity=employee.get();
				
				newEntity.setName(entity.getName());
				newEntity.setAccountNo(entity.getAccountNo());
				
				newEntity=repository.save(newEntity);
				return newEntity;
			}
			else
			{
				entity=repository.save(entity);
				return entity;
			}
	}
}

