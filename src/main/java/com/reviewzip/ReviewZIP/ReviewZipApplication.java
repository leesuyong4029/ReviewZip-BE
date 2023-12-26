package com.reviewzip.ReviewZIP;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ReviewZipApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewZipApplication.class, args);
	}

}
