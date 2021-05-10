package com.mc_3.userCreator.puzzle;

import java.io.IOException;
import java.sql.Blob;
import java.util.Random;
import java.util.stream.Collectors;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mc_3.userCreator.user.User;
import com.mc_3.userCreator.user.UserRepository;

import io.swagger.annotations.ApiOperation;

@Controller
public class FileUploadController {

	private final StorageService storageService;
	
	@Autowired
	private PuzzleRepository puzzleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Value("${upload.path}")
	private String path;
	
	
	public FileUploadController()
	{
		storageService=null;
	}

	@Autowired
	public FileUploadController(StorageService storageService) 
	{
		this.storageService = storageService;
	}

	@ApiOperation(value = "HTML puzzle upload page")
	@GetMapping("/uploadImage")
	public String listUploadedFiles(Model model) throws IOException 
	{

		model.addAttribute("uploadImage", new PuzzleUploadData());
		return "uploadForm";
	}

	@ApiOperation(value = "HTML puzzle upload response page")
	@PostMapping("/uploadImage")
	public String handleFileUpload(@RequestParam("file") MultipartFile file,RedirectAttributes redirectAttributes, Model model, @ModelAttribute PuzzleUploadData uploadImage) throws IOException, SQLException
	{

		model.addAttribute("uploadImage", uploadImage);
		Puzzle p=new Puzzle();
		
		User u=userRepository.findByEmailAndPassword(uploadImage.getUserEmail(), uploadImage.getUserPassword());
		
		if(u==null)//User not found
		{
			return "uploadImageFail";
		}
		else
		{
			Integer idToBe=Math.abs(new Random().nextInt());

			//This is to ensure duplicate IDs don't happen
			while(puzzleRepository.findById(idToBe).isPresent()!=false)
			{
				idToBe=Math.abs(new Random().nextInt());
			}
			
			p.setId(idToBe);
			
			p.setCreatorId(u.getId());//Needs to be updated at a later time
			p.setPuzzleName(uploadImage.getPuzzleName());
			p.setPuzzleType(uploadImage.getPuzzleType());
			p.setPuzzleDifficulty(uploadImage.getPuzzleDifficulty());
			p.setTimesCompleted(0);
			//p.setPuzzleData("http://coms-309-010.cs.iastate.edu/userImages/"+p.getCreatorId()+"/"+file.getOriginalFilename());//User Id based folder system
			p.setPuzzleData("userImages/"+p.getCreatorId()+"/"+file.getOriginalFilename());
			
			storageService.init();
			String tempPath=storageService.getRootLocation();//Gets old path
			
			//storageService.setRootLocation(tempPath+"\\"+p.getCreatorId());//Sets to new path, for local testing
			
			storageService.setRootLocation(tempPath+"/"+p.getCreatorId());//Sets to new path, for server
			storageService.init();//Updates sotrageService
			
			puzzleRepository.save(p);
			storageService.store(file);
			
			storageService.setRootLocation(tempPath);//Sets back to old path as to not tack onto each other
			storageService.init();//Updates sotrageService
			
			//redirectAttributes.addFlashAttribute("message","You successfully uploaded " + file.getOriginalFilename() + "!");
			model.addAttribute("puzzle", p);
	
			return "uploadImageSuccess";
		}
		

	}
	
	@ApiOperation(value = "HTML puzzle view page")
	@GetMapping("/viewImage")
	public String pickID(Model model)
	{

		model.addAttribute("viewImage", new Puzzle());

		return "selectionForm";
	}
	
	@ApiOperation(value = "HTML puzzle upload response page")
	@PostMapping("/viewImage")
	public String displayImage(Model model, @ModelAttribute Puzzle puzzleSelect) throws IOException, SQLException
	{
		Optional<Puzzle> p=puzzleRepository.findById(Integer.parseInt(puzzleSelect.getPuzzleData()));
		Puzzle puz=p.get();
		model.addAttribute("viewImage", puz);

		return "selectionFormResults";
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) 
	{
		return ResponseEntity.notFound().build();
	}

}