package com.mc_3.userCreator;

import org.springframework.data.repository.CrudRepository;

import com.mc_3.userCreator.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Integer> 
{
	User findByEmail(String email);
	User findByPassword(String password);

}