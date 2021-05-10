package com.mc_3.userCreator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class User 
{
  @Id
  private Integer UserID;//Random Number
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private String name="";
  private String email="";//User Input
  private String password="";//User Input
  private boolean admin=true;//false
  private boolean banned=true;//false

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
  
  public void setBan()
  {
	  banned = false;
  }
  
  public boolean getAdmin()
  {
	  return admin;
  }
  
  public void setAdmin()
  {
	  admin =  false;
  }
}
