package tn.esprit.service;

import tn.esprit.entity.Request;

public interface IRequestService {

	Request createRequest(Request request);
	
	void deleteRequest(Long requestId);
	
	Request retrieveRequest(Long requestId);

	Request updateRequest(Long requestId, Request request);

	Request assignSenderToInvitation(Long senderId, Long invitationId);
	
}
