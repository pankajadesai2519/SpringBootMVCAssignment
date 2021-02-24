package com.example.demo.model;

import java.io.Serializable;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name="EMPLOYEE")
public class EmployeeEntity implements Serializable{
	
	private static final long serialVersionUID=1L;
	
	@Id
	@Min(1)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Pattern(regexp="^[a-zA-Z]{1,20}$",message = "Name must be alphanumeric")
	@Column(name="name",nullable=false)
	private String name;
	
	@NotNull
	@Min(value=10000000,message ="Account number must be greater than equal to 100000000")
	@Max(value=999999999,message="Account number must be less than equal to 999999999")
	@Column(name="accountNo",nullable = false)
	private Long accountNo;
	
	
	public EmployeeEntity() {
		super();
	}
	
	public EmployeeEntity(Long id, String name, Long accountNo) {
		super();
		this.id = id;
		this.name = name;
		this.accountNo = accountNo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(Long accountNo) {
		this.accountNo = accountNo;
	}
		
}
