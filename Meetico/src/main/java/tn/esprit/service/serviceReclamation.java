package tn.esprit.service;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.entity.Reclamation;
import tn.esprit.repository.reclamationRepository;





@Service
@Slf4j
public class serviceReclamation implements Ireclamation {
	@Autowired
	reclamationRepository reclamationrepository;

	

	@Override
	public Reclamation ajouterReclamation(Reclamation reclamation) {
		
		return reclamationrepository.save(reclamation);
	}

	@Override
	public void affecterUtilisateurReclamation(Integer idReclamation, Integer userId) {
	/*	Reclamation reclamation = reclamationrepository.findById(idReclamation).orElse(null);
		User user = (User) reclamationrepository.findById(userId).orElse(null);
		reclamation.setUser(user);*/
		
	}
	
	
	

}
