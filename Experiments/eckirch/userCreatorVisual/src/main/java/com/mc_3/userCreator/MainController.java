package com.mc_3.userCreator;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller // This means that this class is a Controller
//@RequestMapping(path="/demo",method = {RequestMethod.GET, RequestMethod.POST}) // This means URL's start with /demo (after Application path)
public class MainController 
{
	@Autowired // This means to get the bean called userRepository
         // Which is auto-generated by Spring, we will use it to handle the data
	private UserRepository userRepository;

	/*
	 * Old way of adding new users, was gross
	 * @PostMapping(path="/add/{name}/{email}/{pass}") // Map ONLY POST Requests
	public @ResponseBody String addNewUser (@PathVariable String name, @PathVariable String email, @PathVariable String pass) 
	{
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

		User n = new User();
		n.setId(Math.abs(new Random().nextInt()));
		n.setName(name);
		n.setEmail(email);
		n.setPassword(pass);
		n.setBan();
		n.setAdmin();
		userRepository.save(n);
		return "Saved";
	}*/

	@GetMapping(path="/all")
	public @ResponseBody Iterable<User> getAllUsers() 
	{
		// This returns a JSON or XML with the users
		return userRepository.findAll();
	}
  

	
	@GetMapping(path="/user")
	public String userForm(Model model) 
	{
		model.addAttribute("user", new User());
	    return "user";
	}

	@PostMapping(path="/user")
	public String userSubmit(@ModelAttribute User user, Model model) 
	{
		model.addAttribute("user", user);
		User n = new User();
		n.setId(Math.abs(new Random().nextInt()));
		n.setName(user.getName());
		n.setEmail(user.getEmail());
		n.setPassword(user.getPassword());
		n.setBan();
		n.setAdmin();
		userRepository.save(n);
	    return "result";
	}
	
	/*@GetMapping("/{email}")
	public String logicCheck(@PathVariable String email)
	{
		User n=userRepository.findByEmail(email);
		String name=n.getName();
		return String.format(name);
	}*/
	
	@GetMapping(path="/login")
	public String loginForm(Model model) 
	{
		model.addAttribute("login", new User());
	    return "login";
	}

	@PostMapping(path="/login")
	public String loginSubmit(@ModelAttribute User user, Model model) 
	{
		if(userRepository.findByEmail(user.getEmail())==userRepository.findByPassword(user.getPassword()))
		{
			user=userRepository.findByEmail(user.getEmail());
			model.addAttribute("login", user);
			return "loginSuccess";
		}
		else
		{
			//Not a user
			return "loginFailed";
		}  
	}
	  

	 
}