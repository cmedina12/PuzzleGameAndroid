package com.mc_3.userCreator.puzzle;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;
/**
 * @author Eric Kirch
 * The Puzzle entity, used for creating the puzzles table via Hibernate, contains methods to get and modify puzzle data
 */

@Entity // This tells Hibernate to make a table out of this class
@Table(name="puzzles")//Tells Hibernate what the name of the table will be
public class Puzzle 
{
	
	  /**
	   *A Random Number, The unique key for each puzzle
	   */
	  @Id
	  @Column(name="puzzleID")
	  private Integer puzzleID;
	  
	  @GeneratedValue(strategy=GenerationType.AUTO)
	  
	  /**
	   *The UserID of the user who has created the puzzle
	   */
	  //@OneToOne(targetEntity=User.class)
	  //@JoinColumn(name="user_id")	 
	  private Integer creatorID;
	  
	  /**
	   *User input, the name of the puzzle
	   */
	  private String PuzzleName;
	  
	  /**
	   *User input, the type of the puzzle
	   *1 = Jigsaw
	   *2 = Slider
	   *3 = Picross
	   */
	  private Integer PuzzleType;
	  
	  /**
	   *User input, the difficulty of the puzzle
	   *1 = Easy
	   *2 = Medium
	   *3 = Hard
	   */
	  private Integer PuzzleDifficulty;
	  
	  /**
	   * The amount of times the puzzle has been completed
	   */
	  private Integer TimesCompleted;
	  
	  /**
	   * The URL as to where the image for the puzzle is stored in the database
	   */
	  private String PuzzleData;
	  
	  public Puzzle()//Used throughout the PuzzleController and FileUploadController files
	  {
		  this.puzzleID=0;
		  this.creatorID=0;
		  this.PuzzleName="No Name";
		  this.PuzzleType=0;
		  this.PuzzleDifficulty=0;
		  this.TimesCompleted=0;
		  this.PuzzleData="N/A";
	  }
	  
	  public Puzzle(Integer puzzleID, Integer creatorID, String PuzzleName, Integer PuzzleType, Integer PuzzleDifficulty, Integer TimesCompleted, String PuzzleData)//Used only for Mockito testing
	  {
		  this.puzzleID=puzzleID;
		  this.creatorID=creatorID;
		  this.PuzzleName=PuzzleName;
		  this.PuzzleType=PuzzleType;
		  this.PuzzleDifficulty=PuzzleDifficulty;
		  this.TimesCompleted=TimesCompleted;
		  this.PuzzleData=PuzzleData;
	  }
	  
	  /**
	   * Getter method for reading the value of puzzleID
	   * @return The global variable puzzleID, an Integer
	   */
	  public Integer getId()
	  {
		  return puzzleID;
	  }
	  
	  /**
	   * Setter method for the global variable puzzleID
	   * @param num: The integer that puzzleID will be set to
	   */
	  public void setId(Integer num)
	  {
		  puzzleID = num;
	  }

	  /**
	   * Getter method for reading the value of CreatorID
	   * @return The global variable CreatorID, an Integer
	   */
	  public Integer getCreatorId()
	  {
		  return creatorID;
	  }
	  
	  /**
	   * Setter method for the global variable CreatorID
	   * @param num: The integer that CreatorID will be set to
	   */
	  public void setCreatorId(Integer num)
	  {
		  creatorID = num;
	  }
	  
	  /**
	   * Getter method for reading the value of puzzleName
	   * @return The global variable puzzleName, a String
	   */
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
	  
	  /**
	   * Getter method for reading the value of TimesCompleted
	   * @return The global variable TimesCompleted, an Integer
	   */
	  public Integer getTimesCompleted()
	  {
		  return TimesCompleted;
	  }
	  
	  /**
	   * Setter method for the global variable TimesCompleted
	   * @param num: The integer that TimesCompleted will be set to
	   */
	  public void setTimesCompleted(Integer num)
	  {
		  TimesCompleted = num;
	  }
	  
	  /**
	   * Getter method for reading the value of PuzzleData
	   * @return The global variable PuzzleData, a String
	   */
	  public String getPuzzleData()
	  {
		  return PuzzleData;
	  }
	  
	  /**
	   * Setter method for the global variable PuzzleData
	   * @param name: The String that PuzzleData will be set to
	   */
	  public void setPuzzleData(String puz)
	  {
		  PuzzleData=puz;
	  }
}
