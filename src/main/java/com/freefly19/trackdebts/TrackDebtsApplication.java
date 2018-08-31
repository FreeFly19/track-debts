package com.freefly19.trackdebts;

import com.freefly19.trackdebts.user.RegisterUserCommand;
import com.freefly19.trackdebts.user.UserService;
import com.freefly19.trackdebts.util.DateTimeProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class TrackDebtsApplication {

	@Bean
	DateTimeProvider dateTimeProvider() {
		return () -> new Date().getTime();
	}

	@Bean
	CommandLineRunner commandLineRunner(UserService userService) {
		return args -> {
			RegisterUserCommand command = RegisterUserCommand.builder()
					.email("user@gmail.com")
					.password("password")
					.build();

			userService.registerUser(command)
					.run(err -> {
						throw new IllegalStateException(err);
					}, a -> {
						System.out.println(command);
					});
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(TrackDebtsApplication.class, args);
	}
}
