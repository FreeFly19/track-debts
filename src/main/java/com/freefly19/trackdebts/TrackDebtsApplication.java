package com.freefly19.trackdebts;

import com.freefly19.trackdebts.util.DateTimeProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Date;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableAsync
public class TrackDebtsApplication {

	@Bean
	DateTimeProvider dateTimeProvider() {
		return () -> new Date().getTime();
	}

	public static void main(String[] args) {
		SpringApplication.run(TrackDebtsApplication.class, args);
	}
}
