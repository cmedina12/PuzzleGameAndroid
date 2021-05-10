package com.mc_3.userCreator.score;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author Eric Kirch
 * The Score entity, used for creating the scores table via Hibernate, contains methods to get and modify score data
 */

@Entity // This tells Hibernate to make a table out of this class
@Table(name="scoresTable")//Tells Hibernate what the name of the table will be
public class Score 
{
	  
	  /**
	   *A Random Number, The unique key for each score
	   */
	  @Id
	  @Column(name="scoreID")
	  private Integer scoreID;
	  
	  @GeneratedValue(strategy=GenerationType.AUTO)
	  
	  /**
	   *The puzzleID of the puzzle the score is for
	   */	  
	  private Integer puzzleScored;
	  
	  /**
	   * Date and time in which the score was made
	   */
	  //@Column(name="TimeCompleted")
	  private Timestamp TimeCompleted;
	  
	  
	  /**
	   *The UserID of the user who has created the puzzle
	   */
	  private Integer scoreMaker;
	  
	  /**
	   * The time in seconds it took to complete the puzzle
	   */
	  private Integer TimeToComplete;
	  
	  public Score()
	  {
		  this.scoreID=0;
		  this.puzzleScored=0;
		  this.TimeCompleted=null;
		  this.scoreMaker=0;
		  this.TimeToComplete=0;  
	  }
	  
	  public Score(Integer scoreID, Integer puzzleScored, Timestamp TimeCompleted, Integer scoreMaker, Integer TimeToComplete)//Used for testing
	  {
		  this.scoreID=scoreID;
		  this.puzzleScored=puzzleScored;
		  this.TimeCompleted=TimeCompleted;
		  this.scoreMaker=scoreMaker;
		  this.TimeToComplete=TimeToComplete;  
	  }
	  
	  /**
	   * Getter method for the value of scoreID
	   * @return The global variable scoreID, an Integer
	   */
	  public Integer getId()
	  {
		  return scoreID;
	  }
	  
	  /**
	   * Setter method for updating the value of scoreID
	   * @param num: The value that scoreID will be set to
	   */
	  public void setId(Integer num)
	  {
		  scoreID = num;
	  }
	  
	  /**
	   * Getter method for the value of PuzzleScored
	   * @return The global variable PuzzleScored, an Integer
	   */
	  public Integer getPuzzleScored()
	  {
		  return puzzleScored;
	  }
	  
	  /**
	   * Setter method for updating the value of PuzzleScored
	   * @param num: The value that PuzzleScored will be set to
	   */
	  public void setPuzzleScored(Integer num)
	  {
		  puzzleScored = num;
	  }
	  
	  /**
	   * Getter method for the value of TimeCompleted
	   * @return The global variable TimeCompleted, as a long
	   */
	  public Timestamp getTimeCompleted()
	  {
		  return TimeCompleted;
	  }
	  
	  /**
	   * Setter method for updating the value of TimeCompleted
	   * @param set: The value that TimeCompleted will be set to
	   */
	  public void setTimeCompleted(Timestamp set)
	  {
		  //To be used  with 
		  //SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		  //System.out.println(formatter.format(TimeCompleted));
		  TimeCompleted = set;
	  }
	  
	  /**
	   * Getter method for the value of scoreMaker
	   * @return The global variable scoreMaker, as an Integer
	   */
	  public Integer getScoreMaker()
	  {
		  return scoreMaker;
	  }
	  
	  /**
	   * Setter method for updating the value of scoreMaker
	   * @param num: The value that scoreMaker will be set to
	   */
	  public void setScoreMaker(Integer num)
	  {
		  scoreMaker = num;
	  }
	  
	  /**
	   * Getter method for the value of TimeToComplete
	   * @return The global variable TimeToComplete, as an Integer
	   */
	  public Integer getTimeToComplete()
	  {
		  return TimeToComplete;
	  }
	  
	  /**
	   * Setter method for updating the value of TimeToCOmplete
	   * @param num: The value that TimeToComplete will be set to
	   */
	  public void setTimeToComplete(Integer num)
	  {
		  TimeToComplete = num;
	  }
}
