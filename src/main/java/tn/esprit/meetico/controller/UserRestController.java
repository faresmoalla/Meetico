package tn.esprit.meetico.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.List;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.meetico.entity.User;
import tn.esprit.meetico.service.IUserService;
import tn.esprit.meetico.util.Credentials;
import tn.esprit.meetico.util.Sort;
import tn.esprit.meetico.util.UserDetails;

@CrossOrigin(allowCredentials = "true", origins = "https://localhost:4200")
@RestController
@Api(tags = "User Management")
@RequestMapping("/user")

public class UserRestController {

	@Autowired
	private IUserService userService;
	
	@ApiOperation(value = "Register an entrepreneur")
	@PostMapping(value = "/registerEntrepreneur")
	public User registerEntrepreneur(@Valid @RequestBody User entrepreneur) throws Exception {
		return userService.registerEntrepreneur(entrepreneur);
	}
	
	@ApiOperation(value = "Authenticate a user")
	@PostMapping("/authenticateUser")
	public UserDetails authenticateUser(@Valid @RequestBody Credentials credentials) {
		return userService.authenticateUser(credentials);
	}

	@ApiOperation(value = "Update a profile")
	@PutMapping("/updateProfile")
	@ResponseBody
	public User updateProfile(@RequestBody User connectedUser) {
		return userService.updateProfile(connectedUser);
	}

	@ApiOperation(value = "Remove a user")
	@DeleteMapping("/removeUser")
	@ResponseBody
	public void removeUser(@RequestParam Long userId) {
		userService.removeUser(userId);
	}
	
	@Transactional
	@ApiOperation(value = "Retrieve all users")
	@GetMapping("/retrieveAllUsers")
	public List<User> retrieveSortedUsers(@RequestParam(defaultValue = "false") Boolean descendant, @RequestParam(defaultValue = "userId") Sort sortedBy) {
		return userService.retrieveSortedUsers(descendant, sortedBy);
	}

	@ApiOperation(value = "Approve a pending employee")
	@PutMapping("/approvePendingEmployee")
	@ResponseBody
	public User approvePendingEmployee(@RequestParam Integer verificationCode) {
		return userService.approvePendingEmployee(verificationCode);
	}

	@ApiOperation(value = "Assign a picture to a user")
	@PutMapping("/assignPictureToUser")
	public User assignPictureToUser(@RequestParam Long userId, @RequestPart("file") MultipartFile file) throws Exception, IOException {
		return userService.assignPictureToUser(userId, file);
	}
	
	@Transactional
	@ApiOperation(value = "Search for users")
	@GetMapping("/searchForUsers")
	@ResponseBody
	public List<User> searchForUsers(@RequestParam String input) {
		return userService.searchForUsers(input);
	}
	
	@ApiOperation(value = "Update a user's status when logged in")
	@PutMapping("/signInStatus")
	@ResponseBody
	public void signInStatus(@RequestParam Long userId) {
		userService.signInStatus(userId);
	}
	
	@ApiOperation(value = "Update a user's status when logged out")
	@PutMapping("/signOutStatus")
	@ResponseBody
	public void signOutStatus(@RequestParam Long userId) {
		userService.signOutStatus(userId);
	}
	
	@ApiOperation(value = "Follow a user")
	@PutMapping("/followUser")
	@ResponseBody
	public void followUser(@RequestParam Long followerId, @RequestParam Long userId) {
		userService.followUser(followerId, userId);
	}

	@ApiOperation(value = "Unfollow a user")
	@PutMapping("/unfollowUser")
	@ResponseBody
	public void unfollowUser(@RequestParam Long followerId, @RequestParam Long userId) {
		userService.unfollowUser(followerId, userId);
	}
	
	@Transactional
	@ApiOperation(value = "Calculate a profile's completion")
	@GetMapping("/calculateProfileCompletion")
	public List<Integer> calculateProfileCompletion() {
		return userService.calculateProfileCompletion();
	}
	
	@Transactional
	@ApiOperation(value = "Count Active Users")
	@GetMapping("/countActiveUsers")
	@ResponseBody
	public Integer countActiveUsers() {
		return userService.countActiveUsers();
	}

}
