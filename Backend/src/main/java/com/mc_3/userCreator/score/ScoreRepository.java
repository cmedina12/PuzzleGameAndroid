package com.mc_3.userCreator.score;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Eric Kirch
 * This will automatically turned into a Bean called scoreRepository, contains methods for finding Scores, as well as helps store them
 */

public interface ScoreRepository extends CrudRepository<Score, Integer> 
{
	List<Score> findByPuzzleScored(Integer PuzzleScored);
	
	Score findByScoreID(Integer ScoreID);
}
