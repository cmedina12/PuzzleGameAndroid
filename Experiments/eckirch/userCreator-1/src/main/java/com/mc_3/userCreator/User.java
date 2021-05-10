package com.mc_3.userCreator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class User 
{
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer UserID;//Random Number
  private String name;
  private String email;//User Input
  private String password;//User Input
  private boolean admin;//false
  private boolean banned;//false

  public Integer getId() 
  {
    return UserID;
  }

  public void setId(Integer id) 
  {
    UserID = id;
  }
  
  public String getName() 
  {
    return name;
  }

  public void setName(String nameA) 
  {
    name = nameA;
  } 

  public String getEmail() 
  {
    return email;
  }

  public void setEmail(String emailA) 
  {
    email = emailA;
  } 
  
  public String getPassword() 
  {
    return password;
  }

  public void setPassword(String name) 
  {
    password = name;
  }
  
  public boolean getBan()
  {
	  return banned;
  }
  
  public boolean setBan()
  {
	  return false;
  }
  
  public boolean getAdmin()
  {
	  return admin;
  }
  
  public boolean setAdmin()
  {
	  return false;
  }
}
