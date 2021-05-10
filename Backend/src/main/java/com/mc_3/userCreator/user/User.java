package com.mc_3.userCreator.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;

import java.util.Random;

/**
 * @author Eric Kirch
 * The User entity, used for creating the users table via Hibernate, contains methods to get and modify user data
 */

@Entity //Tells Hibernate to make a table out of this class
@Table(name="users")//Tells Hibernate what the name of the table will be
public class User 
{
	/**
	 *A Random Number, The unique key for each user
	 */
	@Id
	@Column(name="UserID")
	private Integer UserID;
  
	@GeneratedValue(strategy=GenerationType.AUTO)
  
	/**
	 *User Input, name of the user
	 */
	private String name="";
	
	/**
	 *User Input, email of the user
	 */
	private String email="";
	
	/**
	 *User Input, password of the user
	 */
	private String password="";
	
	/**
	 *predetermined value, set to false when initialized
	 */
	private boolean admin=true;
	
	/**
	 *predetermined value, set to false when initialized
	 */
	private boolean banned=true;

	public User()
	{
		
	}
	
	public User(Integer id, String name, String email, String password, boolean admin, boolean banned)
	{
		UserID=id;
		this.name=name;
		this.email=email;
		this.password=password;
		this.admin=admin;
		this.banned=banned;
	}
	/**
	 * Getter method for reading the value of UserID
	 * @return The global variable UserID, an Integer
	 */
	public Integer getId() 
	{
		return UserID;
	}

	/**
	 * Setter method for the global variable UserID
	 * @param id: The Integer that UserID will be set to
	 */
	public void setId(Integer id) 
	{
		UserID = id;
	}
  
	/**
	 * Getter method for reading the value of name
	 * @return The global variable name, a String
	 */
	public String getName() 
	{
		return name;
	}

	/**
	 * Setter method for the global variable name
	 * @param nameA: The String which name will be set to
	 */
	public void setName(String nameA) 
	{
		name = nameA;
	} 

	/**
	 * Getter method for reading the value of email
	 * @return The global variable email, a String
	 */
	public String getEmail() 
	{
		return email;
	}

	/**
	 * Setter method for the global variable email
	 * @param emailA: The String which email will be set to
	 */
	public void setEmail(String emailA) 
	{
		email = emailA;
	} 
  
	/**
	 * Getter method for reading the value of password
	 * @return The global variable email, a String
	 */
	public String getPassword() 
	{
		return password;
	}

	/**
	 * Setter method for the global variable name
	 * @param nameA: The String which name will be set to
	 */
	public void setPassword(String name) 
	{
		password = name;
	}
  
	/**
	 * Getter method for reading the value of banned
	 * @return The global variable banned, a boolean
	 */
	public boolean getBan()
	{
		return banned;
	}
  
	/**
	 * Setter method for the global variable banned
	 * @param result: The boolean in which banned will be set to
	 */
	public void setBan(boolean result)
	{
		banned = result;
	}
  
	/**
	 * Getter method for reading the value of admin
	 * @return The global variable admin, a boolean
	 */
	public boolean getAdmin()
	{
		return admin;
	}
  
	/**
	 * Setter method for the global variable admin
	 * @param result: The boolean in which admin will be set to
	 */
	public void setAdmin(boolean result)
	{
		admin = result;
	}
}