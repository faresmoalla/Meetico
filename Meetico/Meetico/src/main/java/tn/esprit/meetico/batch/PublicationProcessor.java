package tn.esprit.meetico.batch;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.batch.item.ItemProcessor;

import tn.esprit.meetico.entity.Publication;
import tn.esprit.meetico.entity.Reclamation;
import tn.esprit.meetico.entity.User;
import tn.esprit.meetico.repository.PublicationRepository;
import tn.esprit.meetico.repository.ReclamationRepository;
import tn.esprit.meetico.repository.UserRepository;
import tn.esprit.meetico.service.EmailServiceImpl;

public class PublicationProcessor implements ItemProcessor<User, User> {
	@Autowired
	UserRepository userRepository;

	@Autowired
	PublicationRepository pubRepo;

	@Autowired
	EmailServiceImpl emailServiceImpl;

	@Override
	public User process(User user) throws Exception {
		
List<Publication> list = pubRepo.findAll();
		
		// user.setUser(user);
		for (Publication pub : list ) {
			
			if(pub.getContents().contains("@all") || pub.getContents().contains("@ALL") ||pub.getContents().contains("@all")  ) {
				emailServiceImpl.sendEmail(user.getEmail(), "Dear Users",
						" Une Nouvelle mise a jour \r\n" + ""
								);
				
			}
				
		}
		
		return user;
	
		
		
	}

}
