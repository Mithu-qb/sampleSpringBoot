package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Company;

import com.example.demo.service.ICompanyService;


@RestController

public class CompanyController {

	@Autowired
	ICompanyService iCompanyService;

	@GetMapping("/welcome/companyuser")
	public String welcomeUser() {

		return "welcome mithu";
	}

	@PostMapping(path = "company/addDetails")
	public ResponseEntity<Company> addCompany(@RequestBody Optional<Company> company) {

		// Optional specify alternate values to return or alternate code to run.
		// Optional is a container object which may or may not contain a non-null value.
		if (company.isPresent())
			return new ResponseEntity<Company>(iCompanyService.saveUser(company.get()), HttpStatus.OK);

		return new ResponseEntity<Company>(HttpStatus.BAD_REQUEST);
	}

	@GetMapping(path = "company/getDetails")

	public ResponseEntity<List<Company>> getEmployees() {

		return new ResponseEntity<List<Company>>(iCompanyService.getAllUsers(), HttpStatus.OK);
		// return iEmployeeService.getAllUsers();
	}

	@GetMapping(path = "company/getUser/{id}")

	public ResponseEntity<Optional<Company>> getEmployee(@PathVariable("id") Integer userId) {

		// Returns an Optional with the specified present non-null value else throw
		// exception.
		return new ResponseEntity<Optional<Company>>(iCompanyService.getUserById(Optional.of(userId).get()),
				HttpStatus.OK);
		// return iEmployeeService.getUserById(Optional.of(userId).get());
	}

}