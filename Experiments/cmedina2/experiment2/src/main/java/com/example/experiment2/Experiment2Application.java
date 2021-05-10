package com.example.experiment2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;



@SpringBootApplication
@RestController
public class Experiment2Application {

	public static void main(String[] args) {
		SpringApplication.run(Experiment2Application.class, args);
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}
	
	@GetMapping("/when")
	public String timeAndDate() {
		Date today = new Date();
		return "Today is currently " +  today;
		}
	

}
