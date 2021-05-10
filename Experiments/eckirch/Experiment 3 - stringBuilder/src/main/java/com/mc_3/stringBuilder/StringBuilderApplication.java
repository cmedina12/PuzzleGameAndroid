package com.mc_3.stringBuilder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@SpringBootApplication
@RestController
public class StringBuilderApplication 
{
	/*
	 * Starts program with an empty string (use localhost:8080)
	 * String can be added to with commands (use localhost:8080/"stuff to add")
	 */
	private String theString;
	
	public StringBuilderApplication()
	{
		theString="";
	}
	
	public static void main(String[] args) 
	{
		SpringApplication.run(StringBuilderApplication.class, args);
	}

	@GetMapping("/")
	public String printString()
	{
		return String.format("Current string: %s", theString);//Displays current string
	}
	
    @GetMapping("/{addition}")
    public String addString(@PathVariable String addition)
    {
    	theString+=addition;//Adds to current string
    	return String.format("%s has been added to the String", addition);
    }
}
