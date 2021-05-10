package com.mc_3.userCreator.puzzle;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Eric Kirch
 * This will automatically turned into a Bean called puzzleRepository, contains methods for finding Puzzle, as well as helps store them
 */

public interface PuzzleRepository extends CrudRepository<Puzzle, Integer> 
{
	/**
	 * Find a Puzzle from the database given an id
	 * @param PuzzleID: The Id of a Puzzle to be retrieved
	 * @return A Puzzle in which the PuzzleID matches the given PuzzleID
	 */
	Puzzle findByPuzzleID(Integer PuzzleID);
	
	List<Puzzle> findByCreatorID(Integer creatorID);
	
}
