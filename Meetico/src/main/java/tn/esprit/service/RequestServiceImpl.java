package tn.esprit.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.entity.Request;
import tn.esprit.entity.User;
import tn.esprit.repository.RequestRepository;
import tn.esprit.repository.UserRepository;

@Service
public class RequestServiceImpl implements IRequestService {
	
	@Autowired
	private RequestRepository invitationRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Request createRequest(Request invitation) {
		return invitationRepository.save(invitation);
	}
	
	@Override
	public void deleteRequest(Long invitationId) {
		invitationRepository.deleteById(invitationId);
	}
	
	@Override
	public Request retrieveRequest(Long invitationId) {
		return invitationRepository.findById(invitationId).orElse(null);
	}
	
	@Override
	public Request updateRequest(Long invitationId, Request intermediateRequest) {
		Request invitation = retrieveRequest(invitationId);
		invitation.setEmail(Optional.ofNullable(intermediateRequest.getEmail()).orElse(invitation.getEmail()));
		invitation.setFirstName(Optional.ofNullable(intermediateRequest.getFirstName()).orElse(invitation.getFirstName()));
		invitation.setLastName(Optional.ofNullable(intermediateRequest.getLastName()).orElse(invitation.getLastName()));
		return invitationRepository.save(invitation);
	}

	@Override
	public Request assignSenderToInvitation(Long senderId, Long invitationId) {
		Request invitation = invitationRepository.findById(invitationId).orElse(null);
		User user = userRepository.findById(senderId).orElse(null);
		invitation.setSender(user);
		return invitationRepository.save(invitation);
	}
	
}
