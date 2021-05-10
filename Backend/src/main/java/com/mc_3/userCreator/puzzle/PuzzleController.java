package com.mc_3.userCreator.puzzle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.Random;
import java.sql.Blob;
import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
//import org.springframework.http.ResponseEntity;
import org.springframework.core.io.ByteArrayResource;
//import org.springframework.core.io.Resource;
//import java.util.Optional;
import java.io.ByteArrayInputStream;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;



@Controller
public class PuzzleController 
{
	@Autowired
	private PuzzleRepository puzzleRepository;
	
	@ApiOperation(value = "Delete a puzzle given the ID")
	@GetMapping(path="/deletePuzzle/{id}")
	public @ResponseBody String deletePuzzleByID (@PathVariable Integer id)
	{

		puzzleRepository.deleteById(id);
		return "Deleted";
	}
	
	@ApiOperation(value = "Return all puzzles in the database")
	@GetMapping(path="/allPuzzles")
	public @ResponseBody Iterable<Puzzle> getAllPuzzles() 
	{
		// This returns a JSON or XML with the users
		return puzzleRepository.findAll();
	}
	
	@ApiOperation(value = "Deletes all puzzles in the database")
	@GetMapping(path="/clearPuzzles")
	public @ResponseBody String deleteAllPuzzles() 
	{
		// This returns a JSON or XML with the users
		puzzleRepository.deleteAll();
		return "All puzzles deleted!";
	}
	
	@PostMapping(path="/puzzles") // Map ONLY POST Requests
	public String addNewPuzzle (@RequestParam("userID") Integer userID, @RequestParam("name") String name, @RequestParam("puzzleType") Integer type, @RequestParam("puzzleDifficulty") Integer difficulty, @RequestParam("Image") MultipartFile image) throws IOException, SQLException
	{
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		Puzzle p = new Puzzle();
		
		Integer idToBe=Math.abs(new Random().nextInt());
		//This is to ensure duplicate IDs don't happen
		while(puzzleRepository.findById(idToBe).isPresent()!=false)
		{
			idToBe=Math.abs(new Random().nextInt());
		}
		
		p.setId(idToBe);
		p.setCreatorId(userID);
		p.setPuzzleName(name);
		p.setPuzzleType(type);
		p.setPuzzleDifficulty(difficulty);
		p.setTimesCompleted(0);
		
        /*byte[] file = image.getBytes();
        SerialBlob blob = new SerialBlob(file);
        Blob imageBlob = blob;
        p.setPuzzleData(imageBlob);
        puzzleRepository.save(p);*/

		
		/*image=new MultipartFile("C:\\Users\\gamin\\eclipse-workspace\\userCreatorVisual\\512x512bb.jpg");
		//String image=
		String location=StringUtils.cleanPath(image.getOriginalFilename());
		p.setPuzzleData(location);
        String uploadDir = "user-photos/" + p.getCreatorId();
        FileUploadUtil.saveFile(uploadDir, location, image);
		

		try//Does not work in the slightest
		{
			BufferedImage bImage = ImageIO.read(new File("C:\\Users\\gamin\\eclipse-workspace\\userCreatorVisual\\512x512bb.jpg"));//Data too long, try blobs
	    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    	ImageIO.write(bImage, "png", bos );
	    	byte [] imageData = bos.toByteArray(); 
	    	p.setPuzzleData(imageData);
		}
		catch(Exception e)
		{
			System.out.println("ERROR");
		}*/

		puzzleRepository.save(p);
		return "Your puzzle code is "+p.getId()+" !";
	}
	
	@GetMapping(path = "/getPuzzle/{id}")
	public @ResponseBody Puzzle getPuzzleByID (@PathVariable Integer id)
    {
		//Optional<Puzzle> puzz = puzzleRepository.findById(id);
		//return puzz.get();
		return puzzleRepository.findByPuzzleID(id);//Fix is crucial for notification messages on frontend
    }
	
	@GetMapping(path = "/increasePlayCount/{id}")
	public @ResponseBody String increasePlay (@PathVariable Integer id)
    {
		Optional<Puzzle> puzz = puzzleRepository.findById(id);
		Puzzle p=puzz.get();
		p.setTimesCompleted(p.getTimesCompleted()+1);
		puzzleRepository.save(p);
		return "Times completed updated succesfully!";
    }
	
	@GetMapping(path = "/getTimesPlayed/{id}")
	public @ResponseBody Integer getPlayTimes (@PathVariable Integer id)
    {
		Optional<Puzzle> puzz = puzzleRepository.findById(id);
		return puzz.get().getTimesCompleted();
    }
	
	@GetMapping(path = "/getPuzzleData/{id}")
	public @ResponseBody String viewPuzzleData (@PathVariable Integer id)
    {
		return "Placeholder method, need to test functionality on Frontend";
    }
	
	@ApiOperation(value = "Returns all puzzles for a specific user")
	@GetMapping(path="/puzzleByUser/{uid}")
	public @ResponseBody Iterable<Puzzle> getPuzzleByUser(@PathVariable Integer uid) 
	{
		return puzzleRepository.findByCreatorID(uid);
	}
	
    /**
     * The Response Entity type is set as <Resource>, which can handle files and images very well
     * additional header have to be set to tell the front end what type of conent is being sent from the 
     * backend, thus in this example headers are set.
    */
    /*@GetMapping(path = "/getPuzzle/{id}")
    ResponseEntity<Resource> getLaptopInvoice(@PathVariable int id) throws IOException, SQLException
    {
        
        Puzzle puz = puzzleRepository.findById(id);

        // if user id not found it cannot have an avatar associated with it
        if(puz == null || puz.getPuzzleData() == null)
        {
            return null;
        }
        
        // add headers to state that a file is being downloaded
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+user.getExtenstion());
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        // convert blob to bytearray and set it as response
        int blobLength = (int)user.getAvtar().length();
        byte[] byteArray = user.getAvtar().getBytes(1, blobLength);
        ByteArrayResource data = new ByteArrayResource(byteArray);

        // send the response entity back to the front end with the file
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(blobLength)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(data);
    }

	
	@GetMapping(path="/getPuzzleDataBlob/{id}")
	public @ResponseBody String getPuzzleBlobByID (@PathVariable Integer id)
	{
		try
		{
			Puzzle img=puzzleRepository.findByPuzzleID(id);
			ByteArrayInputStream bis = new ByteArrayInputStream(img.getPuzzleData().getBinaryStream().toString().getBytes());
			System.out.println();
			BufferedImage bImage2 = ImageIO.read(bis);
			ImageIO.write(bImage2, "jpg", new File("output.jpg") );
		}
		catch(Exception e)
		{
			return "CREATION ERROR";
		}
		return "Image Created on local machine";
	}*/
	
	//USED FOR TESTING
	@GetMapping(path="/addPuzzle/{userID}/{name}/{type}/{difficulty}") // Map ONLY POST Requests
	public @ResponseBody String addNewPuzzleURL (@PathVariable Integer userID, @PathVariable String name, @PathVariable Integer type, @PathVariable Integer difficulty)
	{
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		Puzzle p = new Puzzle();
		
		Integer idToBe=Math.abs(new Random().nextInt());
		//This is to ensure duplicate IDs don't happen
		while(puzzleRepository.findById(idToBe).isPresent()!=false)
		{
			idToBe=Math.abs(new Random().nextInt());
		}
		
		p.setId(idToBe);
		p.setCreatorId(userID);
		p.setPuzzleName(name);
		p.setPuzzleType(type);
		p.setPuzzleDifficulty(difficulty);
		p.setTimesCompleted(0);
		p.setPuzzleData(null);
		puzzleRepository.save(p);
		
		return "Saved Temp!";
	}
	

}
