package com.mc_3.userCreator.user;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Eric Kirch
 * This will automatically turned into a Bean called userRepository, contains methods for finding Users, as well as helps store them
 */

public interface UserRepository extends CrudRepository<User, Integer> 
{
	/**
	 * Find a User from the database given an email address
	 * @param email: The email used to find a user
	 * @return A User whose email matches the email given
	 */
	User findByEmail(String email);
	
	/**
	 * Find a User from the database given a password
	 * @param password: The password used to find a user
	 * @return A User whose password matches the password given
	 */
	User findByPassword(String password);
	
	/**
	 * Find a User from the database given an email address and a password
	 * @param email: The email used to find a user
	 * @param password: The password used to find a user
	 * @return A User whose email & password matches the given email & password
	 */
	User findByEmailAndPassword(String email, String password);
	
	
}