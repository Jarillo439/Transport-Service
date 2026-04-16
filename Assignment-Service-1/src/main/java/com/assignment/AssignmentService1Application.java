package com.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AssignmentService1Application {

	public static void main(String[] args) {
		SpringApplication.run(AssignmentService1Application.class, args);
	}

}
