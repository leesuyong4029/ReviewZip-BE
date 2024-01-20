package com.example.ReviewZIP;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication()
public class ReviewZipApplication(exclude = SecurityAutoConfiguration.class) {

	public static void main(String[] args) {
		SpringApplication.run(ReviewZipApplication.class, args);
	}

}
