package tn.esprit.batch;


import java.util.List;

import javax.mail.SendFailedException;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.entity.Reclamation;
import tn.esprit.entity.User;
import tn.esprit.repository.UserRepository;
import tn.esprit.repository.reclamationRepository;
import tn.esprit.service.EmailServiceImpl;

@Slf4j
public class ReclamationProcessor implements ItemProcessor<User, User> {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	reclamationRepository reclamationRepository;
	
	@Autowired
	EmailServiceImpl emailServiceImpl;

	@Override
	
	public User process(User user) throws Exception {
	

	try {
		//User user = userRepository.findById().orElse(null);
		 List<Reclamation> r =reclamationRepository.getAllReclamationsClient(user.getUserId());
	
		
			
				emailServiceImpl.sendEmail(user.getEmail(), "JOIN ProtectHer", " ");
				//user.setUser(user);
				for(Reclamation rr:r) {
					if(rr.getStatus()==false)
						rr.setStatus(true);
				}
				
			
		
	} catch (SendFailedException sendFailedException) {
		log.debug("error: ", sendFailedException.getMessage());
	}
	return user;
}
	
	
	
}
