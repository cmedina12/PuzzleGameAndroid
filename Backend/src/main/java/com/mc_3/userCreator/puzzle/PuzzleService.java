package com.mc_3.userCreator.puzzle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PuzzleService 
{
	@Autowired
	private PuzzleRepository puzzleRepository;
	
	public Puzzle getPuzzleWithID(int i)
	{
		return puzzleRepository.findByPuzzleID(i);
	}
	
	public int getPlayCount(int id)
	{
		return puzzleRepository.findByPuzzleID(id).getTimesCompleted();
	}
	
	public void increasePlayCounter(Puzzle p)
	{
		p.setTimesCompleted(p.getTimesCompleted()+1);
	}
}
