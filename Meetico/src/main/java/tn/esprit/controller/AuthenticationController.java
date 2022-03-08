package tn.esprit.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import tn.esprit.entity.AuthUser;
import tn.esprit.entity.User;
import tn.esprit.service.IUserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Api(tags = "Authentication Management")
@RequestMapping("/authentication")
public class AuthenticationController {
	
	@Autowired
	IUserService userService;

	@ApiOperation(value = "Authenticate a user")
	@PostMapping("/signIn")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthUser user) {
		return userService.authenticateUser(user);
	}

	@ApiOperation(value = "Register a user")
	@PostMapping("/signUp")
	public ResponseEntity<?> registerEntrepreneur(@Valid @RequestBody User entrepreneur) {
		return userService.registerEntrepreneur(entrepreneur);
	}
	
	@ApiOperation(value = "Approve a pending account")
	@PutMapping("/approvePendingAccount")
	@ResponseBody
	public ResponseEntity<?> approvePendingAccount(@RequestParam String verificationCode) {
		return userService.approvePendingAccount(verificationCode);
	}
	
}
