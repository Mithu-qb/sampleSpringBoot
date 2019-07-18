package com.example.demo.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;
import com.example.demo.service.AmazonClient;
import com.example.demo.service.IEmployeeService;



@RestController

public class EmployeeController {

	@Autowired
	private IEmployeeService iEmployeeService;

	@Autowired
	private AmazonClient amazonClient;

	@GetMapping("/welcome/user")
	public String welcomeUser() {

		return "welcome mithu";
	}

	@PostMapping(path = "employee/addEmployeedetails")
	public ResponseEntity<String> addEmployees(@RequestBody Optional<Employee> employee ,@RequestParam Optional<String> companyName) throws IOException {

		//Optional specify alternate values to return or alternate code to run.
		//Optional is a container object which may or may not contain a non-null value.
		if (employee.isPresent()&& companyName.isPresent()) {
			Employee Savedemployee=iEmployeeService.saveUser(employee.get(),companyName.get());
			String result=amazonClient.WriteToFile(Savedemployee);
			//an Optional with a present value if the specified value is non-null
			//otherwise an empty Optional
			if(Optional.ofNullable(result).isPresent()) {
				
				return new ResponseEntity<String>(result, HttpStatus.OK);
			}
		}
		return new ResponseEntity<String >(HttpStatus.BAD_REQUEST);
	}

	@GetMapping(path = "employee/getEmployeedetails")

	public ResponseEntity<List<Employee>> getEmployees() {
		// return iEmployeeService.getAllUsers();
		return new ResponseEntity<List<Employee>>(iEmployeeService.getAllUsers(), HttpStatus.OK);
	}

	@GetMapping(path = "employee/getEuser/{id}")

	public ResponseEntity<Optional<Employee>> getEmployee(@PathVariable("id") Integer userId) {

		// return iEmployeeService.getUserById(Optional.of(userId).get());
		return new ResponseEntity<Optional<Employee>>(iEmployeeService.getUserById(Optional.of(userId).get()),
				HttpStatus.OK);
	}
	/*
	 * if (employeesList.isEmpty())
	 * Optional.ofNullable(employee.get()).ifPresent(employeesList::add);
	 * 
	 * else { Predicate<Employee> predicate = name -> name.getId() !=
	 * employee.get().getId(); if (employeesList.stream().allMatch(predicate))
	 * Optional.ofNullable(employee.get()).ifPresent(employeesList::add); }
	 * employeesList.forEach(employe -> System.out.println("" + employe)); return
	 * employeesList
	 */
}