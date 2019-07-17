package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Company;

import com.example.demo.repository.ICompanyRepository;

@Service
public class CompanyDetailsService implements ICompanyService {

	
	@Autowired
	private ICompanyRepository iCompany;

	/*
	 * lambda expressions can be used to represent the instance of a functional interface.
	 *  A functional interface can have any number of default methods.
	 *  stream(): a sequential Stream over the elements in this collection
	 *  anyMatch()true if any elements of the stream match the provided predicate condition, otherwise false
	 */


	@Override
	public Company saveUser(Company company) {
		List<Company> ComanyList = getAllUsers();

		Predicate<Company> predicate = name -> name.getId() == company.getId();
		if (!ComanyList.stream().anyMatch(predicate))
			iCompany.save(company);
		else
			return null;
		
		return company;
	}

	@Override
	public Optional<Company> getUserById(Integer compId) {
		return iCompany.findById(compId);
	}

	@Override
	public List<Company> getAllUsers() {
		
		return iCompany.findAll();
	}

	@Override
	public Company getCompanyName(String companyName) {
		return iCompany.findByName(companyName);
	}
}
