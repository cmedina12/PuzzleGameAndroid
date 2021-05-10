package com.mc_3.userCreator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class UserCreatorVisualApplication 
{

	public static void main(String[] args) 
	{
		SpringApplication.run(UserCreatorVisualApplication.class, args);
	}

	@GetMapping("/")
	public String defaultMessage()
	{
		return String.format("Please use localhost:8080/user to create a user, and localhost:8080/login to login", 0);
	}
}
