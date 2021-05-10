package com.mc_3.userCreator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mc_3.userCreator.score.Score;
import com.mc_3.userCreator.score.ScoreRepository;
import com.mc_3.userCreator.score.ScoreService;

import static org.mockito.Mockito.when;
import org.junit.jupiter.api.extension.ExtendWith;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)  
public class ScoreTests 
{
	@InjectMocks
	private ScoreService ss;
	
	@Mock
	private ScoreRepository scoreRepository;

	@Test
	public void checkScore()
	{
		assertNotNull(scoreRepository);
		
		Score mockScore = Mockito.mock(Score.class);
		
		when(scoreRepository.findByScoreID(1234567890)).thenReturn(new Score(1234567890,1481081287,null,1200979959,15));//Not even doing anything
		mockScore=ss.getScoreWithID(1234567890);
		
		assertEquals(mockScore.getId(),1234567890);
		assertEquals(mockScore.getPuzzleScored(),1481081287);
		assertEquals(mockScore.getTimeToComplete(),15);
		assertEquals(mockScore.getScoreMaker(),1200979959);

	}
}
