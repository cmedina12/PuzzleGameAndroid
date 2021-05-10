package com.mc_3.userCreator.score;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import io.swagger.annotations.ApiOperation;
import java.util.Optional;
import java.sql.Timestamp;

@Controller
public class ScoreController 
{
	@Autowired
	private ScoreRepository scoreRepository;
	
	@ApiOperation(value = "Returns all scores in the database")
	@GetMapping(path="/allScores")
	//@RequestMapping(method = RequestMethod.GET, value = "/api/javainuse")
	public @ResponseBody Iterable<Score> getAllScores() 
	{
		// This returns a JSON or XML with the users
		return scoreRepository.findAll();
	}
	
	@ApiOperation(value = "Returns all scores for specific puzzle given its PID")
	@GetMapping(path="/scoreByPID/{pid}")
	//@RequestMapping(method = RequestMethod.GET, value = "/api/javainuse")
	public @ResponseBody Iterable<Score> getScoresByPID(@PathVariable Integer pid) 
	{
		return scoreRepository.findByPuzzleScored(pid);
	}
	
	@ApiOperation(value = "Deletes all scores in the database")
	@GetMapping(path="/clearScores")
	public @ResponseBody String deleteAllScores() 
	{
		scoreRepository.deleteAll();
		return "All scores deleted!";
	}
	
	@ApiOperation(value = "Adds a score to the database given the PuzzleID, UserID, and time it took to complete the puzzle")
	@GetMapping(path="/addScore/{pid}/{uid}/{time}")
	public @ResponseBody String addNewScore (@PathVariable Integer pid, @PathVariable Integer uid, @PathVariable Integer time) 
	{
		Score s = new Score();
		
		Integer idToBe=Math.abs(new Random().nextInt());
		//This is to ensure duplicate IDs don't happen
		while(scoreRepository.findById(idToBe).isPresent()!=false)
		{
			idToBe=Math.abs(new Random().nextInt());
		}
		
		s.setId(idToBe);//Score ID, used for sorting and identification
		s.setPuzzleScored(pid);//Puzzle Id
		Timestamp t=new Timestamp(System.currentTimeMillis());
		s.setTimeCompleted(t);//Current Date, bring in from Frontend
		s.setScoreMaker(uid);//User ID
		s.setTimeToComplete(time);//Time it took to complete the puzzle
		scoreRepository.save(s);
		return "Score Saved";
	}
	
	@ApiOperation(value = "Deletes a score from the database given its name")
	@PostMapping(path="/deleteScore/{sid}")
	public @ResponseBody String deleteScore (@PathVariable Integer sid) 
	{
		scoreRepository.deleteById(sid);
		return "Score ID "+sid+" deleted!";
	}
	
	@ApiOperation(value = "Gets a score from the database given its score id")
	@PostMapping(path="/getScore/{sid}")
	public @ResponseBody Score getScore (@PathVariable Integer sid) 
	{
		Optional<Score> score = scoreRepository.findById(sid);
		return score.get();
	}
	
	@ApiOperation(value = "Gets time to complete from the database given its score id")
	@PostMapping(path="/getTime/{sid}")
	public @ResponseBody Integer getTimes(@PathVariable Integer sid) 
	{
		Optional<Score> score = scoreRepository.findById(sid);
		return score.get().getTimeToComplete();
	}
}
