package tn.esprit.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.entity.Reclamation;
import tn.esprit.entity.User;
import tn.esprit.repository.UserRepository;
import tn.esprit.repository.reclamationRepository;






@Service
@Slf4j
public class serviceReclamation implements Ireclamation {
	@Autowired
	reclamationRepository reclamationrepository;
	@Autowired
	UserRepository userrepository;
	

	@Override
	public Reclamation addAffectReclamationUser(Reclamation reclamation, Long userId) {
		User user = userrepository.findById(userId).orElse(null);
		reclamation.setUser(user);
		return reclamationrepository.save(reclamation);
	}



	

	

	
	
	
	

}
