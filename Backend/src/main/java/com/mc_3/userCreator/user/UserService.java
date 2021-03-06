package com.mc_3.userCreator.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService 
{
	@Autowired // This means to get the bean called userRepository// Which is auto-generated by Spring, we will use it to handle the data
	private UserRepository userRepository;
	
	public User getByEmailPass(String Email, String Password)
	{
		User u=userRepository.findByEmailAndPassword(Email, Password);
		
		if(u!=null)
			return u;
		else
			return null;
	}
	
	public User changeEmail(User u, String e)
	{
		u.setEmail(e);
		return u;
	}
	
	public User changeAdmin(User u, boolean a)
	{
		u.setAdmin(a);
		return u;
	}
	
	public User changeBan(User u, boolean b)
	{
		u.setBan(b);
		return u;
	}
	
	public User deleteUser(Integer id)
	{
		userRepository.deleteById(id);
		return null;
	}
}
