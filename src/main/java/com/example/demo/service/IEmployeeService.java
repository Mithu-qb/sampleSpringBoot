package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.Employee;



public interface IEmployeeService {

	Optional<Employee>  getUserById(Integer userId);
	 Employee saveUser(Employee employee, String companyName);
	 
	 List<Employee> getAllUsers();
	
}
