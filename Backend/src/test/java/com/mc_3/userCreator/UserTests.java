package com.mc_3.userCreator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mc_3.userCreator.user.User;
import com.mc_3.userCreator.user.UserRepository;
import com.mc_3.userCreator.user.UserService;

import static org.mockito.Mockito.when;
import org.junit.jupiter.api.extension.ExtendWith;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)  
class UserTests 
{	
	@InjectMocks
	private UserService us;
	
	@Mock
	private UserRepository userRepository;

	@Test
	public void checkUser()
	{
		assertNotNull(userRepository);
		
		User mockUser = Mockito.mock(User.class);
		
		when(userRepository.findByEmailAndPassword("eckirch@iastate.edu","mc_3")).thenReturn(new User(1,"Eric Kirch","eckirch@iastate.edu","mc_3",true,false));//Not even doing anything
		mockUser=us.getByEmailPass("eckirch@iastate.edu","mc_3");
		assertEquals(mockUser.getId(),1);//Gets my User ID
		assertEquals(mockUser.getName(),"Eric Kirch");
		assertEquals(mockUser.getAdmin(),true);
		assertEquals(mockUser.getBan(),false);
	}
	
	@Test
	public void changeEmail()
	{
		assertNotNull(userRepository);
		
		User mockUser = Mockito.mock(User.class);
		
		when(userRepository.findByEmailAndPassword("eckirch@iastate.edu","mc_3")).thenReturn(new User(1,"Eric Kirch","eckirch@iastate.edu","mc_3",true,false));//Not even doing anything
		mockUser=us.getByEmailPass("eckirch@iastate.edu","mc_3");
		mockUser=us.changeEmail(mockUser, "erickirch@iastate.edu");
		assertEquals(mockUser.getEmail(),"erickirch@iastate.edu");//Gets my User ID
	}
	
	@Test
	public void banUser()
	{
		assertNotNull(userRepository);
		
		User mockUser = Mockito.mock(User.class);
		
		when(userRepository.findByEmailAndPassword("eckirch@iastate.edu","mc_3")).thenReturn(new User(1,"Eric Kirch","eckirch@iastate.edu","mc_3",true,false));//Not even doing anything
		mockUser=us.getByEmailPass("eckirch@iastate.edu","mc_3");
		assertEquals(mockUser.getBan(),false);//Gets my User ID
		mockUser=us.changeBan(mockUser, true);
		assertEquals(mockUser.getBan(),true);//Gets my User ID
	}

	@Test
	public void deleteUserTest()
	{
		assertNotNull(userRepository);
		
		User mockUser = Mockito.mock(User.class);
		
		when(userRepository.findByEmailAndPassword("eckirch@iastate.edu","mc_3")).thenReturn(new User(1,"Eric Kirch","eckirch@iastate.edu","mc_3",true,false));
		
		mockUser=us.getByEmailPass("eckirch@iastate.edu","mc_3");
		assertEquals(mockUser.getName(),"Eric Kirch");
		assertEquals(mockUser.getAdmin(),true);
		assertEquals(mockUser.getBan(),false);
		
		mockUser=us.deleteUser(mockUser.getId());
		
		assertEquals(mockUser,null);
	}
}
