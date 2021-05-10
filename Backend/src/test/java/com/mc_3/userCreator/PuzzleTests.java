package com.mc_3.userCreator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mc_3.userCreator.puzzle.Puzzle;
import com.mc_3.userCreator.puzzle.PuzzleRepository;
import com.mc_3.userCreator.puzzle.PuzzleService;

import static org.mockito.Mockito.when;
import org.junit.jupiter.api.extension.ExtendWith;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)  
public class PuzzleTests 
{
	@InjectMocks
	private PuzzleService ps;
	
	@Mock
	private PuzzleRepository puzzleRepository;

	@Test
	public void checkPuzzle()
	{
		assertNotNull(puzzleRepository);
		
		Puzzle mockPuzzle = Mockito.mock(Puzzle.class);
		
		when(puzzleRepository.findByPuzzleID(1481081287)).thenReturn(new Puzzle(1481081287,1200979959,"Trees",2,2,0,"userImages/1200979959/45784wide.jpg"));//Not even doing anything
		mockPuzzle=ps.getPuzzleWithID(1481081287);
		assertEquals(mockPuzzle.getId(),1481081287);
		assertEquals(mockPuzzle.getCreatorId(),1200979959);
		assertEquals(mockPuzzle.getPuzzleName(),"Trees");
		assertEquals(mockPuzzle.getPuzzleType(),2);
		assertEquals(mockPuzzle.getPuzzleDifficulty(),2);
		assertEquals(mockPuzzle.getTimesCompleted(),0);
		assertEquals(mockPuzzle.getPuzzleData(),"userImages/1200979959/45784wide.jpg");

	}
	
	@Test
	public void playCountCheck()
	{
		assertNotNull(puzzleRepository);
		
		Puzzle mockPuzzle = Mockito.mock(Puzzle.class);
		
		when(puzzleRepository.findByPuzzleID(1481081287)).thenReturn(new Puzzle(1481081287,1200979959,"Trees",2,2,0,"userImages/1200979959/45784wide.jpg"));//Not even doing anything
		mockPuzzle=ps.getPuzzleWithID(1481081287);
		assertEquals(mockPuzzle.getTimesCompleted(),0);
		
		ps.increasePlayCounter(mockPuzzle);
		
		assertEquals(mockPuzzle.getTimesCompleted(),1);
	}
}
