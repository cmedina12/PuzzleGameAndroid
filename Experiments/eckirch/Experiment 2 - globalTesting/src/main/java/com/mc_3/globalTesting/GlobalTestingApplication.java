package com.mc_3.globalTesting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@SpringBootApplication
@RestController
public class GlobalTestingApplication
{
	private static int legs;
	private static String name;
	private static String food;

	public GlobalTestingApplication()
	{
		legs=0;
		name="none";
		food="mush";
	}

	@GetMapping("/")
	public String printStatus()
	{
		String printName="Animal type: "+name;
		String printFood="Favorite food: "+food;
		String printLegs="Amount of legs: "+legs;

		return String.format(printName+"\n"+printFood+"\n"+printLegs, 0);//Can't figure out how to get newlines to work
	}

	@GetMapping("/dog")
	public String updateDog()
	{
		legs=4;
		name="Dog";
		food="Hot Dogs";
		return String.format("Updated for dogs!", 0);
	}

	@GetMapping("/cat")
	public String updateCat()
	{
		legs=4;
		name="Cat";
		food="Lasagna";
		return String.format("Updated for cats!", 0);
	}

	@GetMapping("/rabbit")
	public String updateRabbit()
	{
		legs=4;
		name="Rabbits";
		food="Carrots";
		return String.format("Updated for rabbits!", 0);
	}

	@GetMapping("/default")
	public String restoreDefaults()
	{
		legs=0;
		name="none";
		food="mush";
		return String.format("Restored to defaults!", 0);
	}

	public static void main(String[] args)
	{
		SpringApplication.run(GlobalTestingApplication.class, args);
	}

}
