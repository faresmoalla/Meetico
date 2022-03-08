package tn.esprit.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import tn.esprit.entity.User;
import tn.esprit.repository.UserRepository;
import tn.esprit.service.IUserService;

@RestController
@Api(tags = "User Management")
@RequestMapping("/user")

public class UserRestController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private IUserService userService;

	@ApiOperation(value = "Upload a picture to a user")
	@PutMapping("/uploadPictureToUser")
	public User uploadPictureToUser(@RequestParam Long userId,
			@RequestPart("file") MultipartFile file) {
		try {
			User user = userRepository.findById(userId).orElse(null);
			if (user != null) {
				File directory = new File("upload//");
				if (!directory.exists())
					directory.mkdir();
				byte[] bytes = new byte[0];
				bytes = file.getBytes();
				Files.write(Paths.get("upload//" + file.getOriginalFilename()), bytes);
				user.setPicturePath(Paths.get("upload//" + file.getOriginalFilename()).toString());
				return userRepository.save(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@ApiOperation(value = "Update a user")
	@PutMapping("/updateUser")
	@ResponseBody
	public User updateUser(@RequestParam Long userId, @RequestBody User intermediateUser) {
		return userService.updateUser(userId, intermediateUser);
	}

	@ApiOperation(value = "Retrieve an entrepreneur")
	@GetMapping("/retrieveEntrepreneur")
	@ResponseBody
	public User retrieveEntrepreneur(@RequestParam Long entrepreneurId) {
		return userService.retrieveEntrepreneur(entrepreneurId);
	}

	@ApiOperation(value = "Remove an entrepreneur")
	@DeleteMapping("/removeEntrepreneur")
	@ResponseBody
	public String removeEntrepreneur(@RequestParam Long entrepreneurId) {
		userService.removeEntrepreneur(entrepreneurId);
		return "OK";
	}

}
