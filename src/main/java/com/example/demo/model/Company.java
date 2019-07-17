package com.example.demo.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name="company")
public class Company  implements Serializable{

	/**
	 * one to one with employee 
	 * 
	 */
	
	@Id
	private Integer id;
	private String name;

	private String hqCity;
	
	@OneToOne(mappedBy = "company", cascade = CascadeType.ALL , fetch = FetchType.EAGER)
	//ignore infinite recursion if you have bidirectional relationship , add this in parent class
    @JsonIgnore 
	public Employee employeeDetails;

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}


	public String getHqCity() {
		return hqCity;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setHqCity(String hqCity) {
		this.hqCity = hqCity;
	}

	public Employee getEmployeeDetails() {
		return employeeDetails;
	}


	public void setEmployeeDetails(Employee employeeDetails) {
		this.employeeDetails = employeeDetails;
	}



	

}
