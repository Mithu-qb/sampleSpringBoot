package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.Company;
import com.example.demo.model.Employee;



public interface ICompanyService {

	Optional<Company>  getUserById(Integer userId);
	Company getCompanyName(String companyName);
	Company saveUser(Company company);
	 List<Company> getAllUsers();
	
}
