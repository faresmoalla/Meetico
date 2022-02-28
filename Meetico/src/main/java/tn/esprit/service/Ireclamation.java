package tn.esprit.service;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

import tn.esprit.entity.Reclamation;
import tn.esprit.entity.reclamationPriority;
import tn.esprit.entity.reclamationType;

public interface Ireclamation {
	public Reclamation addAffectReclamationUser(Reclamation reclamation,Long userId);
	public Reclamation retrieveReclamation(Integer idReclamation);
	void updateReclamation(Reclamation reclamation);
	public void deleteReclamation(Integer idReclamation);
	public List<Reclamation> ListAllReclamationsAdmin();
	public Set<Reclamation> listReclamationByPriorityAdmin(reclamationPriority pr) throws ParseException;
	public Set<Reclamation> listReclamationByTypeAdmin(reclamationType rt) throws ParseException;
	public Set<Reclamation> listReclamationByPriorityAndTypeAdmin(reclamationPriority pr, reclamationType rt)throws ParseException;
	public List<Reclamation> ListAllReclamationsClient(Long userId);
	public Set<Reclamation> ListReclamationByStatusClient(Long userId);
	public boolean verif(Integer idReclamation);
	
	
	
	
 
	
	

}
