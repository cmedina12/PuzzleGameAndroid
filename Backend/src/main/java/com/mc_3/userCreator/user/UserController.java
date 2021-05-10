package com.mc_3.userCreator.user;

import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import io.swagger.annotations.ApiOperation;

/**
 * @author Eric Kirch
 * Methods related to Users, for use with Puzzle Game
 */
@Controller // This means that this class is a Controller
//@RequestMapping(path="/demo",method = {RequestMethod.GET, RequestMethod.POST}) // This means URL's start with /demo (after Application path)
public class UserController 
{
	/**
	 * The data type which is used in order to manipulate user data
	 */
	@Autowired // This means to get the bean called userRepository// Which is auto-generated by Spring, we will use it to handle the data
	private UserRepository userRepository;
	
	/**
	 * Temporary User that is used to hold the currently logged in User
	 */
	private User login=null;//Logged in user who may change e-mail, password, or delete account
	
	/**
	 * Method to add a new user to the database via the frontend
	 * @param name: Name of the user
	 * @param email: Email of the user
	 * @param pass: Password of the user
	 * @return: Will return "Email is already in use" if email provided matches one already in DB, will return "Saved" elsewhere
	 */
	@ApiOperation(value = "Adds a user to the database given their name, email, and password")
	@PostMapping(path="/addUser/{name}/{email}/{pass}") // Map ONLY POST Requests
	public @ResponseBody String addNewUser (@PathVariable String name, @PathVariable String email, @PathVariable String pass) 
	{
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		User n = new User();
		
		User check=userRepository.findByEmail(email);
		
		if(check!=null)
		{
			return "Email is already in use";
		}
		
		Integer idToBe=Math.abs(new Random().nextInt());
		//This is to ensure duplicate IDs don't happen
		//Implement through other data types, and below - Eric at 2:50 A.M. reminding myself when I wake up :)
		while(userRepository.findById(idToBe).isPresent()!=false)
		{
			idToBe=Math.abs(new Random().nextInt());
		}
		
		n.setId(idToBe);
		n.setName(name);
		n.setEmail(email);
		n.setPassword(pass);
		n.setBan(false);
		n.setAdmin(false);
		userRepository.save(n);
		return "Saved";
	}
	
	/**
	 * Method to change the email of the logged-in user via the frontend
	 * @param email: The current email of the user
	 * @param newEmail: The email that the user will be changing to
	 * @return: Will return "User not found" if email provided doesn't find a user in the DB, will return "Email Changed" if successful, and will return "Email already in use" if there is a user with newEmail
	 */
	//JSON/XML Stuff for Android
	@ApiOperation(value = "Changes a users email given the existing email, and a new one")
	@GetMapping(path="/changeUserEmail/{email}/{newEmail}")
	public @ResponseBody String changeEmailJSON(@PathVariable String email, @PathVariable String newEmail) 
	{
		User n = userRepository.findByEmail(email);//Gets User given current email
		User temp = userRepository.findByEmail(newEmail);//Gets potential user with the newEmail to check if already in use
		
		if(temp!=null)//Sees if new Email is already in use
		{
			return "Email already in use";
		}
		else if(n!=null)//Checks if current user exists (always should)
		{
			n.setEmail(newEmail);
			//userRepository.deleteById(n.getId());
			userRepository.save(n);
			return "Email Changed";
			//Make sure to log the user out
		}
		else//Just to be safe
		{
			return "User not found";
		}
	}
	
	/**
	 * Method to change the password of the logged-in user via the frontend
	 * @param email: The current email of the user whose password will be changed
	 * @param pass: The current password of the logged-in user
	 * @param newPass: The new password of the logged-in user
	 * @return: Will return "User not found" if email provided doesn't find a user in the DB, will return "Password Changed" elsewhere
	 */
	@ApiOperation(value = "Changes a users password given the existing email, password, and the new password")
	@GetMapping(path="/changeUserPassword/{email}/{pass}/{newPass}")
	public @ResponseBody String changePasswordJSON(@PathVariable String email, @PathVariable String pass, @PathVariable String newPass) 
	{
		// This returns a JSON or XML with the users
		User n = userRepository.findByEmailAndPassword(email,pass);
		
		if(n!=null)
		{
			/*User updatedUser=(User)userRepository.load(User.class, n.getId());
			updatedUser.setPassword(newPass);
			userRepository.saveOrUpdate(updatedUser);*/
			
			n.setPassword(newPass);
			//userRepository.deleteById(n.getId());
			userRepository.save(n);
			return "Password Changed";
			//Make sure to log the user out
		}
		else
		{
			return "User not found";
		}	
	}
	
	/**
	 * Method to change the name of the logged-in user via the frontend
	 * @param email: The current email of the user whose password will be changed
	 * @param pass: The current password of the logged-in user
	 * @param newName: The new name of the logged-in user
	 * @return: Will return "User not found" if email provided doesn't find a user in the DB, will return "Name Changed" elsewhere
	 */
	//JSON/XML Stuff for Android
	@ApiOperation(value = "Changes a users name given the existing email, password, and their new name")
	@GetMapping(path="/changeUserName/{email}/{pass}/{newName}")
	public @ResponseBody String changeNameJSON(@PathVariable String email, @PathVariable String newName) 
	{
		User n = userRepository.findByEmail(email);
		
		if(n!=null)
		{
			n.setName(newName);
			//userRepository.deleteById(n.getId());
			userRepository.save(n);
			return "Name Changed";
			//Make sure to log the user out
		}
		else
		{
			return "User not found";
		}

	}
	
	@ApiOperation(value = "Delete account given a user id")
	@GetMapping(path="/deleteUser/{id}")
	public @ResponseBody String deleteAccountJSON(@PathVariable Integer id) 
	{
		Optional<User> u = userRepository.findById(id);
		User n = u.get();
		
		if(n!=null)
		{
			userRepository.deleteById(n.getId());
			return "Account deleted!";
			//Make sure to log the user out
		}
		else
		{
			return "User not found";
		}

	}
	
	/**
	 * Method to get a user given an email and a password
	 * @param email: An email address input by the user
	 * @param pass: A password input by the user
	 * @return: Will return a user given the email and password combo, will return null otherwise
	 */
	@ApiOperation(value = "Finds a user from the database given their email and password")
	@GetMapping(path="/getUser/{email}/{pass}")
	public @ResponseBody User getUserByID(@PathVariable String email, @PathVariable String pass) 
	{
		// This returns a JSON or XML with the users
		return userRepository.findByEmailAndPassword(email,pass);
		
	}
	
	@ApiOperation(value = "Finds a user's name from the database given their id, used for the scoreboard")
	@GetMapping(path="/getUsernameWithID/{id}")
	public @ResponseBody String getUserByID(@PathVariable Integer id) 
	{
		
		Optional<User> use = userRepository.findById(id);
		return use.get().getName();
	}
	
	/**
	 * Method to get all users within the data base
	 * @return: JSON containing every userin the database
	 */
	@ApiOperation(value = "Returns all users in the database")
	@GetMapping(path="/allUsers")
	public @ResponseBody Iterable<User> getAllUsers() 
	{
		// This returns a JSON or XML with the users
		return userRepository.findAll();
	}
	

	/**
	 * First part of a registration page to be displayed in browser, where data is input
	 * @param model: Model that will contain various data types used between methods and web pages
	 * @return: the HTML page whose file name is "user.html"
	 */
	@ApiOperation(value = "HTML user registration page")
	@GetMapping(path="/user")
	public String userForm(Model model) 
	{
		model.addAttribute("user", new User());
	    return "user";
	}

	/**
	 * Second part of a registration page to be displayed in browser, where input data is processed and pushed to database
	 * @param user: User data from part 1
	 * @param model: Model that will contain various data types used between methods and web pages
	 * @return: the HTML page whose file name is "resultExist.html" if email is already in use, "result.html" when email given is not in use
	 */
	@ApiOperation(value = "HTML user registration page response")
	@PostMapping(path="/user")
	public String userSubmit(@ModelAttribute User user, Model model) 
	{
		model.addAttribute("user", user);
		User n = new User();
		
		User check=userRepository.findByEmail(user.getEmail());
		
		if(check!=null)
		{
			return "resultExist";
		}
		
		Integer idToBe=Math.abs(new Random().nextInt());
		//This is to ensure duplicate IDs don't happen
		while(userRepository.findById(idToBe).isPresent()!=false)
		{
			idToBe=Math.abs(new Random().nextInt());
		}
		
		n.setId(idToBe);
		n.setName(user.getName());
		n.setEmail(user.getEmail());
		n.setPassword(user.getPassword());
		n.setBan(false);
		n.setAdmin(false);
		userRepository.save(n);
	    return "result";
	}
	
	/**
	 * First part of a login page to be displayed in browser, where the user inputs their login credentials
	 * @param model: Model that will contain various data types used between methods and web pages
	 * @return: the HTML page whose file name is "login.html"
	 */
	@ApiOperation(value = "HTML user login page")
	@GetMapping(path="/login")
	public String loginForm(Model model) 
	{
		model.addAttribute("login", new User());
	    return "login";
	}

	/**
	 * Second part of a login page to be displayed in browser, will check for user and either log them in, or redirect them elsewhere
	 * @param user: User data from part 1
	 * @param model: Model that will contain various data types used between methods and web pages
	 * @return: "loginBan.html" is displayed if the user found is banned
	 * @return:	"loginSuccessAdmin.html" is displayed if the user found is an admin
	 * @return:	"loginSuccess.html" is displayed if the user is found
	 * @return: "loginFailed.html" is displayed if no user is found
	 */
	@ApiOperation(value = "HTML user login response page")
	@PostMapping(path="/login")
	public String loginSubmit(@ModelAttribute User user, Model model) 
	{
		User tempUser=userRepository.findByEmail(user.getEmail());
		if(tempUser.getPassword().equals(user.getPassword()))//userRepository.findByEmail(user.getEmail())==userRepository.findByPassword(user.getPassword())
		{
			if(userRepository.findByEmail(user.getEmail()).getBan())//User is banned
			{
				return "loginBan";
			}
			else if(userRepository.findByEmail(user.getEmail()).getAdmin())//User is admin
			{
				user=userRepository.findByEmail(user.getEmail());
				model.addAttribute("login", user);
				login=user;
				return "loginSuccessAdmin";
			}
			else
			{
				user=userRepository.findByEmail(user.getEmail());
				model.addAttribute("login", user);
				login=user;
				return "loginSuccess";
			}
			
		}
		else
		{
			//Not a user
			return "loginFailed";
		}  
	}
	
	/**
	 * First part of the change email page to be displayed in browser, user must already be logged in, user inputs login credentials and the new email they'd like
	 * @param model: Model that will contain various data types used between methods and web pages
	 * @return: the HTML page whose file name is "changeEmail.html"
	 */
	@ApiOperation(value = "HTML change email page, must already be logged in")
	@GetMapping(path="/changeEmail")
	public String changeEmailForm(Model model) 
	{
		if(login==null)
		{
			return "redirect:/login";
		}
		
		model.addAttribute("changeEmail", new User());
		return "changeEmail";
	    
	}
	
	/**
	 * Second part of the change email page to be displayed in browser, contains the code that will update a user's email
	 * @param user: User data from part 1
	 * @param model: Model that will contain various data types used between methods and web pages
	 * @return: "changeEmailExists.html" if the given email is already in use
	 * @return: "changeEmailSuccess.html" if email change is a success
	 * @return: "changeEmailFail.html" if user does not exist (Should never be reachable)
	 */
	@ApiOperation(value = "HTML change email respponse page, must already be logged in")
	@PostMapping(path="/changeEmail")
	public String changeEmailSubmit(@ModelAttribute User user, Model model) 
	{
		if(user.getEmail().equals(login.getEmail()) && user.getPassword().equals(login.getPassword()))
		{
			User check=userRepository.findByEmail(user.getEmail());
			
			if(check!=null)//New email already in use
			{
				return "changeEmailExists";
			}
			else//Email not in use
			{
				User replacement=login;
				replacement.setEmail(user.getName());
				//userRepository.deleteById(login.getId());
				userRepository.save(replacement);
				login=null;
				return "changeEmailSuccess";
			}
		}
		else//User doesn't exist
		{
			return "changeEmailFail";
		}

	}
	
	/**
	 * First part of the change password page to be displayed in browser, user must already be logged in, user inputs login credentials and the new password they'd like
	 * @param model: Model that will contain various data types used between methods and web pages
	 * @return: the HTML page whose file name is "changePassword.html"
	 */
	@ApiOperation(value = "HTML change password page, must already be logged in")
	@GetMapping(path="/changePassword")
	public String changePasswordForm(Model model) 
	{
		if(login==null)
		{
			return "redirect:/login";
		}
		
		model.addAttribute("changePassword", new User());
		return "changePassword";
	    
	}
	
	/**
	 * Second part of the change password page to be displayed in browser, contains the code that will update a user's password
	 * @param user: User data from part 1
	 * @param model: Model that will contain various data types used between methods and web pages
	 * @return: "changePasswordSuccess.html" if password change is a success
	 * @return: "changePasswordFail.html" if user does not exist (Should never be reachable)
	 */
	@ApiOperation(value = "HTML change password response page, must already be logged in")
	@PostMapping(path="/changePassword")
	public String changePasswordSubmit(@ModelAttribute User user, Model model) 
	{
		if(user.getEmail().equals(login.getEmail()) && user.getPassword().equals(login.getPassword()))
		{
			User replacement=login;
			replacement.setPassword(user.getName());
			//userRepository.deleteById(login.getId());
			userRepository.save(replacement);
			login=null;
			return "changePasswordSuccess";	
		}
		else
		{
			return "changePasswordFail";
		}

	}
	
	/**
	 * First part of the delete account page to be displayed in browser, user must already be logged in, must put in login credentials again to verify
	 * @param model: Model that will contain various data types used between methods and web pages
	 * @return: the HTML page whose file name is "deleteAccount.html"
	 */
	@ApiOperation(value = "HTML delete account page, must already be logged in")
	@GetMapping(path="/deleteAccount")
	public String deleteAccountForm(Model model) 
	{
		if(login==null)
		{
			return "redirect:/login";
		}
		
		model.addAttribute("deleteAccount", new User());
		return "deleteAccount";
	    
	}
	
	/**
	 * Second part of the delete account page to be displayed in browser, deletes the user from the database
	 * @param user: User data from part 1
	 * @param model: Model that will contain various data types used between methods and web pages
	 * @return: "deleteAccountSuccess.html" if account is deleted from the database
	 * @return: "deleteAccountFail.html" if user does not exist (Should never be reachable)
	 */
	@ApiOperation(value = "HTML delete account response page, must already be logged in")
	@PostMapping(path="/deleteAccount")
	public String deleteAccountSubmit(@ModelAttribute User user, Model model) 
	{
		if(user.getEmail().equals(login.getEmail()) && user.getPassword().equals(login.getPassword()))
		{
			userRepository.deleteById(login.getId());
			login=null;
			return "deleteAccountSuccess";	
		}
		else
		{
			return "deleteAccountFail";
		}

	}
	
	/**
	 * First part of the change ban status page to be displayed in browser, user must already be logged in, page to input user data in order to change their ban status
	 * @param model: Model that will contain various data types used between methods and web pages
	 * @return: the HTML page whose file name is "giveBan.html"
	 */
	@ApiOperation(value = "HTML give ban page, must already be logged in as an admin")
	@GetMapping(path="/giveBan")
	public String banForm(Model model) 
	{
		if(login==null)
		{
			return "redirect:/login";
		}
		
		model.addAttribute("giveBan", new User());
	    return "giveBan";
	}
	
	/**
	 * Second part of the change ban status page to be displayed in browser, changes the ban status of the given user
	 * @param user: User data from part 1
	 * @param model: Model that will contain various data types used between methods and web pages
	 * @return: "giveBanResult.html" if given account's ban status has been changed
	 * @return: "giveBanNoUser.html" if user does not exist
	 */
	@ApiOperation(value = "HTML give ban response page, must already be logged in as an admin")
	@PostMapping(path="/giveBan")
	public String banSubmit(@ModelAttribute User user, Model model) 
	{
		User tempUser=userRepository.findByEmail(user.getEmail());
		if(tempUser.getPassword().equals(user.getPassword()))//userRepository.findByEmail(user.getEmail())==userRepository.findByPassword(user.getPassword())
		{
			User replacement=userRepository.findByEmail(user.getEmail());
			replacement.setBan(!replacement.getBan());
			//userRepository.deleteById(replacement.getId());
			userRepository.save(replacement);
			return "giveBanResult";
			
		}
		else
		{
			//Not a user
			return "giveBanNoUser";
		}  
	}
	
	/**
	 * First part of the change admin status page to be displayed in browser, user must already be logged in, page to input user data in order to change their admin status
	 * @param model: Model that will contain various data types used between methods and web pages
	 * @return: the HTML page whose file name is "giveAdmin.html"
	 */
	@ApiOperation(value = "HTML give admin page, must already be logged in as an admin")
	@GetMapping(path="/giveAdmin")
	public String adminForm(Model model) 
	{
		if(login==null)
		{
			return "redirect:/login";
		}
		
		model.addAttribute("giveAdmin", new User());
	    return "giveAdmin";
	}
	
	/**
	 * Second part of the change admin status page to be displayed in browser, changes the admin status of the given user
	 * @param user: User data from part 1
	 * @param model: Model that will contain various data types used between methods and web pages
	 * @return: "giveAdminResult.html" if given account's admin status has been changed
	 * @return: "giveAdminNoUser.html" if user does not exist
	 */
	@ApiOperation(value = "HTML give admin response page, must already be logged in as an admin")
	@PostMapping(path="/giveAdmin")
	public String adminSubmit(@ModelAttribute User user, Model model) 
	{
		User tempUser=userRepository.findByEmail(user.getEmail());
		if(tempUser.getPassword().equals(user.getPassword()))//userRepository.findByEmail(user.getEmail())==userRepository.findByPassword(user.getPassword())
		{
			User replacement=userRepository.findByEmail(user.getEmail());
			replacement.setAdmin(!replacement.getBan());
			//userRepository.deleteById(replacement.getId());
			userRepository.save(replacement);
			return "giveAdminResult";	
		}
		else
		{
			//Not a user
			return "giveAdminNoUser";
		}  
	}	 
}