package com.example.demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication

/*
 * 	@ComponentScan: enable @Component scan on the package where the application is located in root pkge 
 * 	if not in root location , specify package using @ComponentScan
 */
public class SpringBootdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootdemoApplication.class, args);
		
	}

}
 