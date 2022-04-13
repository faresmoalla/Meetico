package tn.esprit.meetico.service;

import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.meetico.entity.User;
import tn.esprit.meetico.util.Member;
import tn.esprit.meetico.util.UserAttribute;

public interface IUserService {
	
	ResponseEntity<String> registerEntrepreneur(User entrepreneur);
	
	ResponseEntity<Integer> registerEmployee(User employee);

	ResponseEntity<String> authenticateUser(@Valid Member user);
	
	ResponseEntity<String> updateUser(String username, User updation);
	
	ResponseEntity<String> removeUser(Long userId);
	
	ResponseEntity<List<User>> retrieveAllUsers();
		
	ResponseEntity<String> approvePendingEmployee(Integer verificationCode);
	
	ResponseEntity<String> assignPictureToUser(Long userId, MultipartFile file);
	
	ResponseEntity<List<User>> retrieveSortedUsers(List<UserAttribute> userAttributes, Boolean ascendant);
	
	ResponseEntity<List<User>> searchForUsers(String input);

	ResponseEntity<String> accountManagement();

}
