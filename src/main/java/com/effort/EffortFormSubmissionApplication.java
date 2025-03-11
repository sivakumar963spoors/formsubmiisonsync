package com.effort;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.effort")
public class EffortFormSubmissionApplication {

	public static void main(String[] args) {
		SpringApplication.run(EffortFormSubmissionApplication.class, args);
	}

}
