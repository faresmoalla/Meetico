package tn.esprit.meetico.service;

import java.util.List;

import tn.esprit.meetico.entity.Request;

public interface IRequestService {

	Request createRequest(Request request);
		
	Request updateRequest(Long requestId, Request updation);

	void deleteRequest(Long requestId);
	
	List<Request> retrieveAllRequests(Long userId);

	Request assignSenderToRequest(Long senderId, Long invitationId);

	List<Request> searchForRequests(String input);

}
