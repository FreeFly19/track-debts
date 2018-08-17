package com.freefly19.trackdebts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class TrackDebtsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrackDebtsApplication.class, args);
	}
}
