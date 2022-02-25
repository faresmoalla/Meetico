package tn.esprit.service;


import java.util.List;

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
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Reclamation> listReclamationByPriorityAdmin() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Reclamation> listReclamationByTypeAdmin() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Reclamation> listReclamationByPriorityAndTypeAdmin() {
		// TODO Auto-generated method stub
		return null;
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
