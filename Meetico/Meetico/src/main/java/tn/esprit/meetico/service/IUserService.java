package tn.esprit.meetico.service;

import java.io.IOException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.meetico.entity.User;
import tn.esprit.meetico.util.Credentials;
import tn.esprit.meetico.util.Sort;
import tn.esprit.meetico.util.UserDetails;

public interface IUserService {
	
	User registerEntrepreneur(User entrepreneur) throws Exception;
	
	User registerEmployee(User employee);

	UserDetails authenticateUser(@Valid Credentials user);
	
	User updateProfile(User connectedUser);
	
	void removeUser(Long userId);
	
	List<User> retrieveAllUsers(Boolean descendant, Sort sortedBy);
				
	User approvePendingEmployee(Integer verificationCode);
	
	User assignPictureToUser(Long userId, MultipartFile file) throws Exception, IOException;
	
	List<User> searchForUsers(String input);
	
	void signInStatus(Long userId);
		
	void signOutStatus(Long userId);

	void followUser(Long followerId, Long userId);
	
	void unfollowUser(Long followerId, Long userId);
	
	List<String> uploadConvertablePDF(MultipartFile file) throws Exception, IOException;

	List<Integer> calculateProfileCompletion();
	
	List<Object> accountStatistics();
	
}
