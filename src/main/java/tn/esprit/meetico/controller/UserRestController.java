package tn.esprit.meetico.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import tn.esprit.meetico.util.AuthUser;
import tn.esprit.meetico.util.UserAttribute;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Api(tags = "User Management")
@RequestMapping("/user")

public class UserRestController {

	@Autowired
	private IUserService userService;
	
	@ApiOperation(value = "Register an entrepreneur")
	@PostMapping("/registerEntrepreneur")
	public ResponseEntity<String> registerEntrepreneur(@Valid @RequestBody User entrepreneur) {
		return userService.registerEntrepreneur(entrepreneur);
	}
	
	@ApiOperation(value = "Authenticate a user")
	@PostMapping("/authenticateUser")
	public ResponseEntity<String> authenticateUser(@Valid @RequestBody AuthUser user) {
		return userService.authenticateUser(user);
	}

	@ApiOperation(value = "Update a user")
	@PutMapping("/updateUser")
	@ResponseBody
	public ResponseEntity<String> updateUser(HttpServletRequest request, @RequestBody User updation) {
        String username = request.getUserPrincipal().getName();
		return userService.updateUser(username, updation);
	}

	@ApiOperation(value = "Remove a user")
	@DeleteMapping("/removeUser")
	@ResponseBody
	public ResponseEntity<String> removeUser(@RequestParam Long userId) {
		return userService.removeUser(userId);
	}
	
	@Transactional
	@ApiOperation(value = "Retrieve all users")
	@GetMapping("/retrieveAllUsers")
	public ResponseEntity<List<User>> retrieveAllUsers() {
		return userService.retrieveAllUsers();
	}

	@ApiOperation(value = "Approve a pending employee")
	@PutMapping("/approvePendingEmployee")
	@ResponseBody
	public ResponseEntity<String> approvePendingEmployee(@RequestParam Integer verificationCode) {
		return userService.approvePendingEmployee(verificationCode);
	}

	@ApiOperation(value = "Assign a picture to a user")
	@PutMapping("/assignPictureToUser")
	public ResponseEntity<String> assignPictureToUser(@RequestParam Long userId, @RequestPart("file") MultipartFile file) {
		return userService.assignPictureToUser(userId, file);
	}
	
	@Transactional
	@ApiOperation(value = "Retrieve sorted users")
	@GetMapping("/retrieveSortedUsers")
	public ResponseEntity<List<User>> retrieveSortedUsers(@RequestParam List<UserAttribute> userAttributes, @RequestParam(required = false, defaultValue = "true") Boolean ascendant) {
		return userService.retrieveSortedUsers(userAttributes, ascendant);
	}
	
	@Transactional
	@ApiOperation(value = "Search for users")
	@GetMapping("/searchForUsers")
	@ResponseBody
	public ResponseEntity<List<User>> searchForUsers(@RequestParam String input) {
		return userService.searchForUsers(input);
	}

}
