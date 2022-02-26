package tn.esprit.service;


import java.text.ParseException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.entity.Reclamation;
import tn.esprit.entity.User;
import tn.esprit.entity.reclamationPriority;
import tn.esprit.entity.reclamationType;
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

	@Override
	public Reclamation retrieveReclamation(Integer idReclamation) {
		
		return reclamationrepository.findById(idReclamation).orElse(null);
	}
	@Override
	public void updateReclamation(Reclamation reclamation) {
		
		Reclamation R = retrieveReclamation(reclamation.getIdReclamation());
		R.setDescription(reclamation.getDescription());
		R.setFile(reclamation.getFile());
		R.setLastModificationDate(reclamation.getLastModificationDate());
		R.setPicture(reclamation.getPicture());
		R.setPriority(reclamation.getPriority());
		R.setType(reclamation.getType());
		reclamationrepository.save(R);	
	}


	@Override
	public void deleteReclamation(Integer idReclamation) {
		
		reclamationrepository.deleteById(idReclamation);
		
	}


	@Override
	public List<Reclamation> ListAllReclamationsAdmin() {
		
		return reclamationrepository.findAll();
	}


	@Override
	public Set<Reclamation> listReclamationByPriorityAdmin(reclamationPriority pr) {
		
		return reclamationrepository.getReclamationByPriority(pr);
	}


	@Override
	public Set<Reclamation> listReclamationByTypeAdmin(reclamationType rt) {
		
		return reclamationrepository.getReclamationByType(rt);
	}


	@Override
	public Set<Reclamation> listReclamationByPriorityAndTypeAdmin(reclamationPriority pr, reclamationType rt) {
		
		return reclamationrepository.getReclamationByPriorityAndType(pr, rt);
	}


	@Override
	public List<Reclamation> listReclamationByStatusClient() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean verif(Reclamation reclamation) {
		// TODO Auto-generated method stub
		return false;
	}

	



	

	

	
	
	
	

}
