package tn.esprit.service;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import tn.esprit.entity.AuthUser;
import tn.esprit.entity.User;

public interface IUserService {
	
	ResponseEntity<?> registerEntrepreneur(User entrepreneur);
	
	User registerEmployee(User employee);

	ResponseEntity<?> authenticateUser(@Valid AuthUser user);
	
	void removeEntrepreneur(Long entrepreneurId);
	
	User updateUser(Long userId, User intermediateUser);
	
	User retrieveEntrepreneur(Long entrepreneurId);
		
	Boolean verifyEmployeeStatus(String verificationCode);
	
	ResponseEntity<?> approvePendingAccount(String verificationCode);

	void accountManagement();


}
