package tn.esprit.meetico.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.meetico.entity.Role;
import tn.esprit.meetico.entity.User;
import tn.esprit.meetico.repository.UserRepository;
import tn.esprit.meetico.security.JWTUtils;
import tn.esprit.meetico.util.AuthUser;
import tn.esprit.meetico.util.UserAttribute;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JWTUtils jwtUtils;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private UserRepository userRepository;

	@Override
	public ResponseEntity<String> registerEntrepreneur(User entrepreneur) {
		if (userRepository.existsByUsername(entrepreneur.getUsername())) {
			return ResponseEntity.badRequest().body("Username is already taken.");
		}
		if (userRepository.existsByEmail(entrepreneur.getEmail())) {
			return ResponseEntity.badRequest().body("Email is already in use.");
		}
		entrepreneur.setPassword(encoder.encode(entrepreneur.getPassword()));
		entrepreneur.setRole(Role.ENTREPRENEUR);
		entrepreneur.setActive(true);
		userRepository.save(entrepreneur);
		return ResponseEntity.ok().body("Entrepreneur registered successfully.");
	}

	@Override
	public ResponseEntity<Integer> registerEmployee(User employee) {
		employee.setActive(false);
		employee.setPassword(encoder.encode(employee.getPassword()));
		employee.setPhoneNumber(employee.getPhoneNumber());
		employee.setRole(Role.EMPLOYEE);
		employee.setVerificationCode(ThreadLocalRandom.current().nextInt(100000, 1000000));
		userRepository.save(employee);
		return ResponseEntity.ok().body(employee.getVerificationCode());
	}

	public ResponseEntity<String> authenticateUser(AuthUser user) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		String jwt = jwtUtils.generateJwtToken(authentication);
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		return ResponseEntity.ok().body("You are logged in as an" + roles.get(0).toString() + " with token : " + jwt);
	}

	@Override
	public ResponseEntity<String> updateUser(String username, User updation) {
		User user = userRepository.findByUsername(username);
		if (user != null) {
			user.setAddress(Optional.ofNullable(updation.getAddress()).orElse(user.getAddress()));
			user.setBirthday(Optional.ofNullable(updation.getBirthday()).orElse(user.getBirthday()));
			user.setEmail(Optional.ofNullable(updation.getEmail()).orElse(user.getEmail()));
			user.setFirstName(Optional.ofNullable(updation.getFirstName()).orElse(user.getFirstName()));
			user.setGender(Optional.ofNullable(updation.getGender()).orElse(user.getGender()));
			user.setLastName(Optional.ofNullable(updation.getLastName()).orElse(user.getLastName()));
			user.setPassword(Optional.ofNullable(updation.getPassword()).orElse(user.getPassword()));
			user.setUsername(Optional.ofNullable(updation.getUsername()).orElse(user.getUsername()));
			userRepository.save(user);
			return ResponseEntity.ok().body("Employee updated successfully.");
		}
		return ResponseEntity.badRequest().body("No correspondance.");
	}

	@Override
	public ResponseEntity<String> removeUser(Long userId) {
		User user = userRepository.findById(userId).orElse(null);
		if (user != null) {
			userRepository.deleteById(userId);
			return ResponseEntity.ok().body("User deleted successfully.");
		}
		return ResponseEntity.badRequest().body("No correspondance.");
	}

	@Override
	public ResponseEntity<List<User>> retrieveAllUsers() {
		return ResponseEntity.ok().body(userRepository.findAll());
	}

	@Override
	public ResponseEntity<String> approvePendingEmployee(Integer verificationCode) {
		User employee = userRepository.findByVerificationCode(verificationCode);
		if (employee != null && !employee.getActive()) {
			employee.setActive(true);
			employee.setVerificationCode(null);
			userRepository.save(employee);
			return ResponseEntity.ok().body("Employee approved successfully.");
		}
		return ResponseEntity.badRequest().body("No correspondance.");
	}

	@Override
	public ResponseEntity<String> assignPictureToUser(Long userId, MultipartFile file) {
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
				userRepository.save(user);
				return ResponseEntity.ok().body("Picture uploaded successfully.");
			}
			return ResponseEntity.badRequest().body("No correspondance.");
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Picture upload failed.");
		}
	}

	@Override
	public ResponseEntity<List<User>> retrieveSortedUsers(List<UserAttribute> userAttributes, Boolean ascendant) {
		Integer size = userRepository.findAll().size();
		if (ascendant) {
			if (userAttributes.size() == 1)
				return ResponseEntity.ok().body(userRepository.findAll(
						PageRequest.of(0, size, Sort.by(userAttributes.get(0).toString()).ascending()).getSort()));
			else if (userAttributes.size() == 2)
				return ResponseEntity.ok()
						.body(userRepository.findAll(PageRequest
								.of(0, size,
										Sort.by(userAttributes.get(0).toString())
												.and(Sort.by(userAttributes.get(1).toString())).ascending())
								.getSort()));
			else if (userAttributes.size() == 3)
				return ResponseEntity.ok()
						.body(userRepository.findAll(PageRequest
								.of(0, size,
										Sort.by(userAttributes.get(0).toString())
												.and(Sort.by(userAttributes.get(1).toString()))
												.and(Sort.by(userAttributes.get(2).toString())).ascending())
								.getSort()));
			else if (userAttributes.size() == 4)
				return ResponseEntity.ok()
						.body(userRepository.findAll(PageRequest.of(0, size,
								Sort.by(userAttributes.get(0).toString()).and(Sort.by(userAttributes.get(1).toString()))
										.and(Sort.by(userAttributes.get(2).toString()))
										.and(Sort.by(userAttributes.get(3).toString())).ascending())
								.getSort()));
			else if (userAttributes.size() == 5)
				return ResponseEntity.ok()
						.body(userRepository.findAll(PageRequest.of(0, size,
								Sort.by(userAttributes.get(0).toString()).and(Sort.by(userAttributes.get(1).toString()))
										.and(Sort.by(userAttributes.get(2).toString()))
										.and(Sort.by(userAttributes.get(3).toString()))
										.and(Sort.by(userAttributes.get(4).toString())).ascending())
								.getSort()));
			else if (userAttributes.size() == 6)
				return ResponseEntity.ok()
						.body(userRepository.findAll(PageRequest.of(0, size,
								Sort.by(userAttributes.get(0).toString()).and(Sort.by(userAttributes.get(1).toString()))
										.and(Sort.by(userAttributes.get(2).toString()))
										.and(Sort.by(userAttributes.get(3).toString()))
										.and(Sort.by(userAttributes.get(4).toString()))
										.and(Sort.by(userAttributes.get(5).toString())).ascending())
								.getSort()));
			else if (userAttributes.size() == 7)
				return ResponseEntity.ok().body(userRepository.findAll(PageRequest.of(0, size, Sort
						.by(userAttributes.get(0).toString()).and(Sort.by(userAttributes.get(1).toString()))
						.and(Sort.by(userAttributes.get(2).toString())).and(Sort.by(userAttributes.get(3).toString()))
						.and(Sort.by(userAttributes.get(4).toString())).and(Sort.by(userAttributes.get(5).toString()))
						.and(Sort.by(userAttributes.get(6).toString())).ascending()).getSort()));
			else if (userAttributes.size() == 8)
				return ResponseEntity.ok().body(userRepository.findAll(PageRequest.of(0, size, Sort
						.by(userAttributes.get(0).toString()).and(Sort.by(userAttributes.get(1).toString()))
						.and(Sort.by(userAttributes.get(2).toString())).and(Sort.by(userAttributes.get(3).toString()))
						.and(Sort.by(userAttributes.get(4).toString())).and(Sort.by(userAttributes.get(5).toString()))
						.and(Sort.by(userAttributes.get(6).toString())).and(Sort.by(userAttributes.get(7).toString()))
						.ascending()).getSort()));
			else
				return ResponseEntity.ok().body(userRepository.findAll(PageRequest.of(0, size, Sort
						.by(userAttributes.get(0).toString()).and(Sort.by(userAttributes.get(1).toString()))
						.and(Sort.by(userAttributes.get(2).toString())).and(Sort.by(userAttributes.get(3).toString()))
						.and(Sort.by(userAttributes.get(4).toString())).and(Sort.by(userAttributes.get(5).toString()))
						.and(Sort.by(userAttributes.get(6).toString())).and(Sort.by(userAttributes.get(7).toString()))
						.and(Sort.by(userAttributes.get(8).toString())).ascending()).getSort()));
		} else {
			if (userAttributes.size() == 1)
				return ResponseEntity.ok().body(userRepository.findAll(
						PageRequest.of(0, size, Sort.by(userAttributes.get(0).toString()).descending()).getSort()));
			else if (userAttributes.size() == 2)
				return ResponseEntity.ok()
						.body(userRepository.findAll(PageRequest
								.of(0, size,
										Sort.by(userAttributes.get(0).toString())
												.and(Sort.by(userAttributes.get(1).toString())).descending())
								.getSort()));
			else if (userAttributes.size() == 3)
				return ResponseEntity.ok()
						.body(userRepository.findAll(PageRequest
								.of(0, size,
										Sort.by(userAttributes.get(0).toString())
												.and(Sort.by(userAttributes.get(1).toString()))
												.and(Sort.by(userAttributes.get(2).toString())).descending())
								.getSort()));
			else if (userAttributes.size() == 4)
				return ResponseEntity.ok()
						.body(userRepository.findAll(PageRequest.of(0, size,
								Sort.by(userAttributes.get(0).toString()).and(Sort.by(userAttributes.get(1).toString()))
										.and(Sort.by(userAttributes.get(2).toString()))
										.and(Sort.by(userAttributes.get(3).toString())).descending())
								.getSort()));
			else if (userAttributes.size() == 5)
				return ResponseEntity.ok()
						.body(userRepository.findAll(PageRequest.of(0, size,
								Sort.by(userAttributes.get(0).toString()).and(Sort.by(userAttributes.get(1).toString()))
										.and(Sort.by(userAttributes.get(2).toString()))
										.and(Sort.by(userAttributes.get(3).toString()))
										.and(Sort.by(userAttributes.get(4).toString())).descending())
								.getSort()));
			else if (userAttributes.size() == 6)
				return ResponseEntity.ok()
						.body(userRepository.findAll(PageRequest.of(0, size,
								Sort.by(userAttributes.get(0).toString()).and(Sort.by(userAttributes.get(1).toString()))
										.and(Sort.by(userAttributes.get(2).toString()))
										.and(Sort.by(userAttributes.get(3).toString()))
										.and(Sort.by(userAttributes.get(4).toString()))
										.and(Sort.by(userAttributes.get(5).toString())).descending())
								.getSort()));
			else if (userAttributes.size() == 7)
				return ResponseEntity.ok().body(userRepository.findAll(PageRequest.of(0, size, Sort
						.by(userAttributes.get(0).toString()).and(Sort.by(userAttributes.get(1).toString()))
						.and(Sort.by(userAttributes.get(2).toString())).and(Sort.by(userAttributes.get(3).toString()))
						.and(Sort.by(userAttributes.get(4).toString())).and(Sort.by(userAttributes.get(5).toString()))
						.and(Sort.by(userAttributes.get(6).toString())).descending()).getSort()));
			else if (userAttributes.size() == 8)
				return ResponseEntity.ok().body(userRepository.findAll(PageRequest.of(0, size, Sort
						.by(userAttributes.get(0).toString()).and(Sort.by(userAttributes.get(1).toString()))
						.and(Sort.by(userAttributes.get(2).toString())).and(Sort.by(userAttributes.get(3).toString()))
						.and(Sort.by(userAttributes.get(4).toString())).and(Sort.by(userAttributes.get(5).toString()))
						.and(Sort.by(userAttributes.get(6).toString())).and(Sort.by(userAttributes.get(7).toString()))
						.descending()).getSort()));
			else
				return ResponseEntity.ok().body(userRepository.findAll(PageRequest.of(0, size, Sort
						.by(userAttributes.get(0).toString()).and(Sort.by(userAttributes.get(1).toString()))
						.and(Sort.by(userAttributes.get(2).toString())).and(Sort.by(userAttributes.get(3).toString()))
						.and(Sort.by(userAttributes.get(4).toString())).and(Sort.by(userAttributes.get(5).toString()))
						.and(Sort.by(userAttributes.get(6).toString())).and(Sort.by(userAttributes.get(7).toString()))
						.and(Sort.by(userAttributes.get(8).toString())).descending()).getSort()));
		}
	}

	@Override
	public ResponseEntity<List<User>> searchForUsers(String input) {
		List<User> users = userRepository.findAllByInput(input);
		if (users != null)
			return ResponseEntity.ok().body(users);
		return ResponseEntity.badRequest().body(null);
	}

	@Override
	public ResponseEntity<String> accountManagement() {
		Integer number = userRepository.findByActive(false).size();
		return ResponseEntity.ok().body("We have detected " + number + " accounts' status showed as Pending.");
	}

}