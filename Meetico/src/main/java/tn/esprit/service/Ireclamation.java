package tn.esprit.service;

import java.util.List;


import tn.esprit.entity.Reclamation;

public interface Ireclamation {
	public Reclamation addAffectReclamationUser(Reclamation reclamation,Long userId);
	public Reclamation retrieveReclamation(Integer idReclamation);
	void updateReclamation(Reclamation reclamation);
	public void deleteReclamation(Integer idReclamation);
	public List<Reclamation> ListAllReclamationsAdmin();
	public List<Reclamation> listReclamationByPriorityAdmin();
	public List<Reclamation> listReclamationByTypeAdmin();
	public List<Reclamation> listReclamationByPriorityAndTypeAdmin();
	public List<Reclamation> listReclamationByStatusClient();
	public boolean verif(Reclamation reclamation);
	
	
	
 
	
	

}
