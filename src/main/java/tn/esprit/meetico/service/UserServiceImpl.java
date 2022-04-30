package tn.esprit.meetico.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import tn.esprit.meetico.util.Credentials;
import tn.esprit.meetico.util.UserDetails;

@Service
public class UserServiceImpl implements IUserService {

	@Value("C:/Users/Aminous/Documents/GitHub/MeeticoFront-End/src/assets/upload/")
	private String uploadPath;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JWTUtils jwtUtils;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private UserRepository userRepository;

	@Override
	public User registerEntrepreneur(User entrepreneur) throws Exception {
		if (userRepository.existsByUsername(entrepreneur.getUsername()))
			throw new Exception("This username is already taken.");
		if (userRepository.existsByEmail(entrepreneur.getEmail()))
			throw new Exception("This email is already taken.");
		entrepreneur.setPassword(encoder.encode(entrepreneur.getPassword()));
		entrepreneur.setRole(Role.ENTREPRENEUR);
		entrepreneur.setActive(true);
		return userRepository.save(entrepreneur);
	}

	@Override
	public User registerEmployee(User employee) {
		employee.setActive(false);
		employee.setPassword(encoder.encode(employee.getPassword()));
		employee.setPhoneNumber(employee.getPhoneNumber());
		employee.setRole(Role.EMPLOYEE);
		employee.setVerificationCode(ThreadLocalRandom.current().nextInt(100000, 1000000));
		return userRepository.save(employee);
	}

	@Override
	public UserDetails authenticateUser(Credentials credentials) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		User user = userRepository.findByUsername(userDetails.getUsername());
		return new UserDetails(user, jwtUtils.generateJwtToken(authentication));
	}

	@Override
	public User updateProfile(User user) {
		User connectedUser = userRepository.findById(user.getUserId()).get();
		connectedUser.setActivityFields(
				Optional.ofNullable(user.getActivityFields()).orElse(connectedUser.getActivityFields()));
		connectedUser.setAddress(Optional.ofNullable(user.getAddress()).orElse(connectedUser.getAddress()));
		connectedUser.setBirthday(Optional.ofNullable(user.getBirthday()).orElse(connectedUser.getBirthday()));
		connectedUser.setEmail(Optional.ofNullable(user.getEmail()).orElse(connectedUser.getEmail()));
		connectedUser.setFirstName(Optional.ofNullable(user.getFirstName()).orElse(connectedUser.getFirstName()));
		connectedUser.setGender(Optional.ofNullable(user.getGender()).orElse(connectedUser.getGender()));
		connectedUser.setLastName(Optional.ofNullable(user.getLastName()).orElse(connectedUser.getLastName()));
		if (user.getPassword() != null && !user.getPassword().startsWith("$2a$10$"))
			connectedUser.setPassword(encoder.encode(user.getPassword()));
		else
			connectedUser.setPassword(connectedUser.getPassword());
		connectedUser.setPhoneNumber(Optional.ofNullable(user.getPhoneNumber()).orElse(connectedUser.getPhoneNumber()));
		connectedUser.setProfessions(Optional.ofNullable(user.getProfessions()).orElse(connectedUser.getProfessions()));
		connectedUser.setRequests(Optional.ofNullable(user.getRequests()).orElse(connectedUser.getRequests()));
		connectedUser.setUsername(Optional.ofNullable(user.getUsername()).orElse(connectedUser.getUsername()));
		return userRepository.save(connectedUser);
	}

	@Override
	public void removeUser(Long userId) {
		userRepository.deleteById(userId);
	}

	@Override
	public List<User> retrieveAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User approvePendingEmployee(Integer verificationCode) {
		User employee = userRepository.findByVerificationCode(verificationCode);
		if (employee != null) {
			employee.setActive(true);
			employee.setVerificationCode(null);
			userRepository.save(employee);
			return userRepository.save(employee);
		}
		return employee;
	}

	@Override
	public User assignPictureToUser(Long userId, MultipartFile file) throws Exception, IOException {
		User user = userRepository.findById(userId).get();
		File directory = new File(uploadPath);
		if (!directory.exists())
			throw new Exception("Please change your upload directory.");
		byte[] bytes = new byte[0];
		bytes = file.getBytes();
		Files.write(Paths.get(uploadPath + file.getOriginalFilename()), bytes);
		user.setPicture("assets/upload/" + file.getOriginalFilename());
		return userRepository.save(user);
	}

	@Override
	public List<User> retrieveSortedUsers(Boolean descendant, tn.esprit.meetico.util.Sort sortedBy) {
		Integer size = userRepository.findAll().size();
		if (sortedBy.equals(tn.esprit.meetico.util.Sort.name)) {
			if (!descendant) return userRepository.findAll(PageRequest.of(0, size, (Sort.by("firstName").and(Sort.by("lastName"))).ascending()).getSort());
			else return userRepository.findAll(PageRequest.of(0, size, (Sort.by("firstName").and(Sort.by("lastName"))).descending()).getSort());
		}
		else {
			if (!descendant) return userRepository.findAll(PageRequest.of(0, size, Sort.by(sortedBy.toString()).ascending()).getSort());
			else return userRepository.findAll(PageRequest.of(0, size, Sort.by(sortedBy.toString()).descending()).getSort());
		}
	}

	@Override
	public List<User> searchForUsers(String input) {
		List<User> users = userRepository.findAllByInput(input);
		return users;
	}

	@Override
	public void signInStatus(Long userId) {
		User user = userRepository.findById(userId).get();
		user.setLastSeen(null);
		userRepository.save(user);
	}

	@Override
	public void signOutStatus(Long userId) {
		User user = userRepository.findById(userId).get();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.HOUR_OF_DAY, 1);
		user.setLastSeen(calendar.getTime());
		userRepository.save(user);
	}

	@Override
	public void followUser(Long followerId, Long userId) {
		User follower = userRepository.findById(followerId).get();
		User user = userRepository.findById(userId).get();
		List<User> followers = user.getFollowers();
		followers.add(follower);
		user.setFollowers(followers);
		userRepository.save(user);
	}

	@Override
	public void unfollowUser(Long followerId, Long userId) {
		User follower = userRepository.findById(followerId).get();
		User user = userRepository.findById(userId).get();
		List<User> followers = user.getFollowers();
		followers.remove(follower);
		user.setFollowers(followers);
		userRepository.save(user);
	}

	@Override
	public List<Integer> calculateProfileCompletion() {
		List<Integer> list = new ArrayList<Integer>();
		for (User user : retrieveAllUsers()) {
			Integer counter = 0;
			if (!user.getActivityFields().isEmpty())
				counter++;
			if (user.getAddress() != null)
				counter++;
			if (user.getBirthday() != null)
				counter++;
			if (user.getEmail() != null)
				counter++;
			if (user.getFirstName() != null)
				counter++;
			if (user.getGender() != null)
				counter++;
			if (user.getLastName() != null)
				counter++;
			if (user.getPhoneNumber() != null)
				counter++;
			if (user.getPicture() != null)
				counter++;
			if (!user.getProfessions().isEmpty())
				counter++;
			list.add(counter * 10);
		}
		return list;
	}

	@Override
	public Integer countActiveUsers() {
		return userRepository.findByActive(false).size();
	}

}