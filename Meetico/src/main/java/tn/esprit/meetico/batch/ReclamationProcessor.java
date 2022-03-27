package tn.esprit.meetico.batch;

import java.io.File;
import java.util.List;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import tn.esprit.meetico.entity.Reclamation;
import tn.esprit.meetico.entity.User;
import tn.esprit.meetico.repository.UserRepository;
import tn.esprit.meetico.repository.ReclamationRepository;
import tn.esprit.meetico.service.EmailServiceImpl;

public class ReclamationProcessor implements ItemProcessor<User, User> {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ReclamationRepository reclamationRepository;

	@Autowired
	EmailServiceImpl emailServiceImpl;

	@Override
	public User process(User user) throws Exception {
		
		List<Reclamation> r = reclamationRepository.getAllReclamationsClient(user.getUserId());

		emailServiceImpl.sendEmail(user.getEmail(), "JOIN ProtectHer",
				" Dear user\r\n" + " We were unable to answer your Reclamation,\r\n"
						+ "If it is important, please resend.\r\n" + "Cordialement");
		// user.setUser(user);
		
		for (Reclamation rr : r) {
			if (rr.getStatus() == false)
				rr.setStatus(true);
		}
		return user;
	
	}

}
