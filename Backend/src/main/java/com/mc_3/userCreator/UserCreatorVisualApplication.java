package com.mc_3.userCreator;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mc_3.userCreator.puzzle.StorageProperties;
import com.mc_3.userCreator.puzzle.StorageService;

/**
 * @author Group mc_3
 * Class that boots up the rest of the program and initalizes some things
 */
@SpringBootApplication//Used to signify it's the main class
@EnableConfigurationProperties(StorageProperties.class)//Used for configuring StorageServices
public class UserCreatorVisualApplication 
{

	/**
	 * Main class where the application is run from
	 **/
	public static void main(String[] args) 
	{
		SpringApplication.run(UserCreatorVisualApplication.class, args);
	}

	/**
	 * Bean used for initial configuration of StorageService, used in the FileUploadController
	 * @param storage: A storage service object that will be initialized
	 * @return Runs storage.init() given the configurations
	 */
	/*@Bean
	CommandLineRunner init(StorageService storage) 
	{
		return (args) -> 
		{
			storage.init();
		};
	}*/
	//Very fixable problem
	//4516502455
}
