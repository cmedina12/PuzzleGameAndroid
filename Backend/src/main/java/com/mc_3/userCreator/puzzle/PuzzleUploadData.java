package com.mc_3.userCreator.puzzle;

public class PuzzleUploadData 
{
	private String userEmail;
	
	private String userPassword;
	
	private String PuzzleName;
	  
	private Integer PuzzleType;
	  
	private Integer PuzzleDifficulty;
	
	public String getUserEmail() 
	{
		return userEmail;
	}

	/**
	 * Setter method for the global variable email
	 * @param emailA: The String which email will be set to
	 */
	public void setUserEmail(String emailA) 
	{
		userEmail = emailA;
	} 
  
	/**
	 * Getter method for reading the value of password
	 * @return The global variable email, a String
	 */
	public String getUserPassword() 
	{
		return userPassword;
	}

	/**
	 * Setter method for the global variable name
	 * @param nameA: The String which name will be set to
	 */
	public void setUserPassword(String name) 
	{
		userPassword = name;
	}
	
	  public String getPuzzleName()
	  {
		  return PuzzleName;
	  }
	  
	  /**
	   * Setter method for the global variable PuzzleName
	   * @param name: The String that PuzzleName will be set to
	   */
	  public void setPuzzleName(String name)
	  {
		  PuzzleName = name;
	  }
	  
	  /**
	   * Getter method for reading the value of PuzzleType
	   * @return The global variable PuzzleType, an Integer
	   */
	  public Integer getPuzzleType()
	  {
		  return PuzzleType;
	  }
	  
	  /**
	   * Setter method for the global variable PuzzleType
	   * @param num: The integer that PuzzleType will be set to
	   */
	  public void setPuzzleType(Integer num)
	  {
		  PuzzleType = num;
	  }
	  
	  /**
	   * Getter method for reading the value of PuzzleDifficulty
	   * @return The global variable PuzzleDifficulty, an Integer
	   */
	  public Integer getPuzzleDifficulty()
	  {
		  return PuzzleDifficulty;
	  }
	  
	  /**
	   * Setter method for the global variable PuzzleDifficulty
	   * @param num: The integer that PuzzleDifficulty will be set to
	   */
	  public void setPuzzleDifficulty(Integer num)
	  {
		  PuzzleDifficulty = num;
	  }
	  
}
