package tn.esprit.service;


import java.text.ParseException;
import java.util.List;
import java.util.Set;



import tn.esprit.entity.Reclamation;
import tn.esprit.entity.reclamationPriority;
import tn.esprit.entity.reclamationType;

public interface Ireclamation {

	public Reclamation addAffectReclamationUser(Reclamation reclamation,Long userId,Integer idPicture);
	public Reclamation retrieveReclamation(Integer idReclamation);
	public void updateReclamation(Reclamation reclamation)throws ParseException ;
	public void deleteReclamation(Integer idReclamation);
	public List<Reclamation> ListAllReclamationsAdmin();
	public Set<Reclamation> listReclamationByPriorityAdmin(reclamationPriority pr) throws ParseException;
	public Set<Reclamation> listReclamationByTypeAdmin(reclamationType rt) throws ParseException;
	public Set<Reclamation> listReclamationByPriorityAndTypeAdmin(reclamationPriority pr, reclamationType rt)throws ParseException;
	public List<Reclamation> ListAllReclamationsClient(Long userId);
	public Set<Reclamation> ListReclamationByStatusClient(Long userId);
	public boolean verif(Integer idReclamation);
	void answerAdmin(Reclamation reclamation)throws ParseException; 
	public float statWatingReclamationByPriorityAndType(reclamationType type ,reclamationPriority priority);
	public float statWatingReclamation();
	public float statWatingReclamationByType(reclamationType type );
	public float statWatingReclamationByType(reclamationPriority priority );
	public void answerReclamation(String answer,Integer idReclamation);
	public float statReclamationsTreterNonTreter();

	
	
	
	
 
	
	

}
