package tn.esprit.meetico.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tn.esprit.meetico.entity.Request;
import tn.esprit.meetico.entity.Status;
import tn.esprit.meetico.entity.User;
import tn.esprit.meetico.repository.RequestRepository;
import tn.esprit.meetico.repository.UserRepository;

@Service
public class RequestServiceImpl implements IRequestService {

	@Autowired
	private RequestRepository requestRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public ResponseEntity<String> createRequest(Request request) {
		String firstName = request.getFirstName().replaceAll("\\s+", " ");
		firstName = firstName.replaceFirst("\\s", "");
		firstName = replaceLast(firstName, " ", "");
		firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
		firstName = capitalizeWord(firstName);
		String lastName = request.getLastName().replaceAll("\\s+", " ");
		lastName = lastName.replaceFirst("\\s", "");
		lastName = replaceLast(lastName, " ", "");
		lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);
		lastName = capitalizeWord(lastName);
		request.setEmail(request.getEmail().toLowerCase());
		request.setFirstName(firstName);
		request.setLastName(lastName);
		request.setSendTime(null);
		request.setStatus(Status.UNSENT);
		requestRepository.save(request);
		return ResponseEntity.ok().body("Request created successfully");
	}
	
	 private String replaceLast(String string, String find, String replace) {
		 int lastIndex = string.lastIndexOf(find);
		 if (lastIndex == -1) return string;
		 String beginString = string.substring(0, lastIndex);
		 String endString = string.substring(lastIndex + find.length());
		 return beginString + replace + endString;
	}
	 
	 private String capitalizeWord(String str) {  
		 String words[] = str.split("\\s");  
		 String capitalizeWord = "";  
		 for (String w : words) {  
			 String first = w.substring(0,1);  
			 String afterfirst = w.substring(1);  
			 capitalizeWord += first.toUpperCase() + afterfirst + " ";
		 } 
		 return capitalizeWord.trim();  
	 }

	@Override
	public ResponseEntity<String> updateRequest(Long requestId, Request updation) {
		Request request = requestRepository.findById(requestId).orElse(null);
		if (request != null) {
			request.setEmail(Optional.ofNullable(updation.getEmail()).orElse(request.getEmail()));
			request.setFirstName(
					Optional.ofNullable(updation.getFirstName()).orElse(request.getFirstName()));
			request.setLastName(Optional.ofNullable(updation.getLastName()).orElse(request.getLastName()));
			requestRepository.save(request);
			return ResponseEntity.ok().body("Request updated successfully.");
		}
		return ResponseEntity.badRequest().body("No correspondance.");
	}
	
	@Override
	public ResponseEntity<String> deleteRequest(Long requestId) {
		Request request = requestRepository.findById(requestId).orElse(null);
		if (request != null) {
			requestRepository.deleteById(requestId);
			return ResponseEntity.ok().body("Request deleted successfully.");
		}
		return ResponseEntity.badRequest().body("No correspondance.");
	}

	@Override
	public ResponseEntity<String> assignSenderToRequest(Long senderId, Long requestId) {
		Request request = requestRepository.findById(requestId).orElse(null);
		User user = userRepository.findById(senderId).orElse(null);
		if (request != null && user != null) {
			request.setSender(user);
			requestRepository.save(request);
			return ResponseEntity.ok().body("Sender assigned successfully.");
		}
		return ResponseEntity.badRequest().body("No correspondance.");
	}

}
