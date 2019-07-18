package com.example.demo.service;



import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Employee;
import com.example.demo.repository.IEmployeeRepository;


@Service
public class EmployeeDetailsService  implements IEmployeeService{
	
	@Autowired
	private IEmployeeRepository iEmployee;
	@Autowired
	private ICompanyService iCompanyService;
	
	@Override
	public Optional<Employee> getUserById(Integer userId) {
		
		return iEmployee.findById(userId);
	}

	@Override
	public Employee saveUser(Employee employee, String companyName) {
		List <Employee> employeesList = getAllUsers();
		
		/*
		 * lambda expressions can be used to represent the instance of a functional interface.
		 *  A functional interface can have any number of default methods.
		 *  stream(): a sequential Stream over the elements in this collection
		 *  anyMatch()true if any elements of the stream match the provided predicate, otherwise false
		 *  an Optional with a present value if the specified value is non-nul
		 */
	
			Predicate<Employee> predicate = name -> name.getId() == employee.getId();
			if (!employeesList.stream().anyMatch(predicate)) {
				employee.setCompany(iCompanyService.getCompanyName(companyName));
				if(Optional.ofNullable(employee.getCompany()).isPresent()) {
					
					return iEmployee.save(employee);
				}
						
			}
	
				return null;
	
	}

	@Override
	public List<Employee> getAllUsers() {
		
		return iEmployee.findAll();
		//return Optional.ofNullable(employeesList).isPresent()? employeesList:null;
		
	}
}
