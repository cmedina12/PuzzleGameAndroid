package com.mc_3.randomArraySort;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Random;
import java.util.Arrays;

@SpringBootApplication
@RestController
public class RandomArraySortApplication {

	public static void main(String[] args) {
		SpringApplication.run(RandomArraySortApplication.class, args);
	}
	
	@GetMapping("/")
	public String printNum()
	{
		Random r=new Random();
		return String.format("Random Number: %d", r.nextInt());
	}
	
    @GetMapping("/{amount}")
    public String randArraySort(@PathVariable int amount)
    {
    	int [] nums = new int[amount];
    	Random r=new Random();
    	
    	for(int i=0; i<amount; i++)
    	{
    		nums[i]=r.nextInt();
    	}
    	
    	Arrays.parallelSort(nums);
    	return String.format("Sorted Array: %s", Arrays.toString(nums));
    }


}
