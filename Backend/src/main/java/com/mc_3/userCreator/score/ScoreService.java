package com.mc_3.userCreator.score;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ScoreService 
{
	@Autowired
	private ScoreRepository scoreRepository;
	
	public Score getScoreWithID(int i)
	{
		return scoreRepository.findByScoreID(i);
	}
}
