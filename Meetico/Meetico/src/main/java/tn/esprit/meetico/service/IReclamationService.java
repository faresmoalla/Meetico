package tn.esprit.meetico.service;


import java.text.ParseException;
import java.util.List;
import java.util.Set;



import tn.esprit.meetico.entity.Reclamation;
import tn.esprit.meetico.entity.User;
import tn.esprit.meetico.entity.reclamationPriority;
import tn.esprit.meetico.entity.reclamationType;

public interface IReclamationService {

	public Reclamation addAffectReclamationUser(Reclamation reclamation, User user,Integer idPicture);
	public Reclamation retrieveReclamation(Integer idReclamation);
	public void updateReclamation(Reclamation reclamation/*, User user*/)throws ParseException ;
	public void deleteReclamation(Integer idReclamation/*, User user*/);
	public List<Reclamation> ListAllReclamationsAdmin();
	public Set<Reclamation> listReclamationByPriorityAdmin(reclamationPriority pr) throws ParseException;
	public Set<Reclamation> listReclamationByTypeAdmin(reclamationType rt) throws ParseException;
	public Set<Reclamation> listReclamationByPriorityAndTypeAdmin(reclamationPriority pr, reclamationType rt)throws ParseException;
	public List<Reclamation> ListAllReclamationsClient(Long userId);
	public Set<Reclamation> ListReclamationByStatusClient(Long userId);
	public boolean verif(Integer idReclamation);
	void answerAdmin(Reclamation reclamation,User user)throws ParseException; 
	public float statWatingReclamationByPriorityAndType(reclamationType type ,reclamationPriority priority);
	public float statWatingReclamation();
	public float statWatingReclamationByType(reclamationType type );
	public float statWatingReclamationByType(reclamationPriority priority );
	//public void answerReclamation(String answer,Integer idReclamation);
	public float statReclamationsTreterNonTreter();

	
	
	
	
 
	
	

}
