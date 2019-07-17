package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.example.demo.model.Company;


public interface ICompanyRepository extends JpaRepository<Company, Integer> {
	
	
	@Query("Select c FROM Company c WHERE c.name = ?1")
	Company findByName(String name);

}
