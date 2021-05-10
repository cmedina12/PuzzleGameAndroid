package com.mc_3.userCreator.report;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mc_3.userCreator.report.Report;
import com.mc_3.userCreator.score.Score;

/**
 * @author Eric Kirch
 * This will automatically turned into a Bean called reportRepository, contains methods for finding Reports, as well as helps store them
 */

public interface ReportRepository extends CrudRepository<Report, Integer> 
{
	List<Report> findByPuzzleId(Integer puzzleId);
}
