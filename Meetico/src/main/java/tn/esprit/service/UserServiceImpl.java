package tn.esprit.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import tn.esprit.entity.AuthUser;
import tn.esprit.entity.JWTResponse;
import tn.esprit.entity.MessageResponse;
import tn.esprit.entity.Role;
import tn.esprit.entity.User;
import tn.esprit.repository.UserRepository;
import tn.esprit.security.JWTUtils;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private JWTUtils jwtUtils;

	@Override
	public ResponseEntity<?> registerEntrepreneur(User entrepreneur) {
		if (userRepository.existsByUsername(entrepreneur.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Username is already taken!"));
		}

		if (userRepository.existsByEmail(entrepreneur.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Email is already in use!"));
		}
		entrepreneur.setPassword(encoder.encode(entrepreneur.getPassword()));
		entrepreneur.setRole(Role.ENTREPRENEUR);
		entrepreneur.setActive(true);
		userRepository.save(entrepreneur);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));

	}

	@Override
	public User registerEmployee(User employee) {
		employee.setActive(false);
		employee.setPassword(encoder.encode(employee.getPassword()));
		employee.setRole(Role.EMPLOYEE);
		employee.setVerificationCode(RandomString.make(64));
		return userRepository.save(employee);
	}

	public ResponseEntity<?> authenticateUser(AuthUser user) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(
				new JWTResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}

	@Override
	public void removeEntrepreneur(Long entrepreneurId) {
		User entrepreneur = retrieveEntrepreneur(entrepreneurId);
		if (entrepreneur != null)
			userRepository.deleteById(entrepreneurId);
	}
	
	@Override
	public User updateUser(Long userId, User intermediateUser) {
		User user = userRepository.findById(userId).orElse(null);
		user.setAddress(Optional.ofNullable(intermediateUser.getAddress()).orElse(user.getAddress()));
		user.setBirthday(Optional.ofNullable(intermediateUser.getBirthday()).orElse(user.getBirthday()));
		user.setEmail(Optional.ofNullable(intermediateUser.getEmail()).orElse(user.getEmail()));
		user.setFirstName(Optional.ofNullable(intermediateUser.getFirstName()).orElse(user.getFirstName()));
		user.setGender(Optional.ofNullable(intermediateUser.getGender()).orElse(user.getGender()));
		user.setLastName(Optional.ofNullable(intermediateUser.getLastName()).orElse(user.getLastName()));
		user.setPassword(Optional.ofNullable(intermediateUser.getPassword()).orElse(user.getPassword()));
		user.setUsername(Optional.ofNullable(intermediateUser.getUsername()).orElse(user.getUsername()));
		return userRepository.save(user);
	}

	@Override
	public User retrieveEntrepreneur(Long entrepreneurId) {
		return userRepository.findEntrepreneurById(entrepreneurId);
	}

	@Override
	public Boolean verifyEmployeeStatus(String verificationCode) {
		User user = userRepository.findEmployeeByVerificationCode(verificationCode);
		if (user == null || user.getActive()) {
			return false;
		} else {
			user.setVerificationCode(null);
			user.setActive(true);
			userRepository.save(user);

			return true;
		}
	}

	@Override
	@Scheduled(fixedRate = 60000)
	public void accountManagement() {
		Integer number = userRepository.findByActive(false).size();
		log.info("We have detected " + number + " accounts' status showed as Pending");
	}

	@Override
	public ResponseEntity<?> approvePendingAccount(String verificationCode) {
		User employee = userRepository.findByVerificationCode(verificationCode);
		if (employee != null) {
			employee.setActive(true);
			employee.setVerificationCode(null);
			return ResponseEntity.ok(new MessageResponse("Employee approved successfully!"));
		}
		return ResponseEntity.badRequest().body(new MessageResponse("Nothing found!"));
	}

}