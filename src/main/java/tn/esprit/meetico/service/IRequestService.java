package tn.esprit.meetico.service;

import java.util.List;

import tn.esprit.meetico.entity.Request;
import tn.esprit.meetico.entity.User;

public interface IRequestService {

	Request createRequest(Request request);
		
	Request updateRequest(Long requestId, Request updation);

	void deleteRequest(Long requestId);
	
	List<Request> retrieveAllRequests(Long userId);

	User assignSenderToRequest(Long requestId, Long senderId);

	List<Request> searchForRequests(String input);

}
