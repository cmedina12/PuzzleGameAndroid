package com.mc_3.userCreator.report;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;

/**
 * @author Eric Kirch
 * The Score entity, used for creating the scores table via Hibernate, contains methods to get and modify score data
 */

@Entity // This tells Hibernate to make a table out of this class
@Table(name="report")//Tells Hibernate what the name of the table will be
public class Report
{
	  
	  /**
	   *A Random Number, The unique key for each report
	   */
	  @Id
	  @Column(name="reportID")
	  private Integer reportID;
	  
	  @GeneratedValue(strategy=GenerationType.AUTO)
	  
	  /**
	   *The puzzleID of the puzzle that is reported
	   */	  
	  private Integer puzzleId;
	  
	  /**
	   * Description of the report
	   */
	  private String reportDescription;
	   
	  /**
	   * Getter method for the value of scoreID
	   * @return The global variable scoreID, an Integer
	   */
	  public Integer getId()
	  {
		  return reportID;
	  }
	  
	  /**
	   * Setter method for updating the value of scoreID
	   * @param num: The value that scoreID will be set to
	   */
	  public void setId(Integer num)
	  {
		  reportID = num;
	  }
	  
	  /**
	   * Getter method for the value of PuzzleScored
	   * @return The global variable PuzzleScored, an Integer
	   */
	  public Integer getPuzzleId()
	  {
		  return puzzleId;
	  }
	  
	  /**
	   * Setter method for updating the value of PuzzleScored
	   * @param num: The value that PuzzleScored will be set to
	   */
	  public void setPuzzleId(Integer num)
	  {
		  puzzleId = num;
	  }
	  
	  /**
	   * Getter method for the value of TimeCompleted
	   * @return The global variable TimeCompleted, as a long
	   */
	  public String getReportDescription()
	  {
		  return reportDescription;
	  }
	  
	  /**
	   * Setter method for updating the value of TimeCompleted
	   * @param set: The value that TimeCompleted will be set to
	   */
	  public void setReportDescription(String set)
	  {
		  reportDescription = set;
	  }
	  
}