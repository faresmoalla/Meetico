package tn.esprit.meetico.service;

import org.springframework.http.ResponseEntity;
import tn.esprit.meetico.entity.Request;

public interface IRequestService {

	ResponseEntity<String> createRequest(Request request);
		
	ResponseEntity<String> updateRequest(Long requestId, Request updation);

	ResponseEntity<String> deleteRequest(Long requestId);

	ResponseEntity<String> assignSenderToRequest(Long senderId, Long invitationId);

}
