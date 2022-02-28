package tn.esprit.service;

import java.util.List;
import java.util.Set;

import tn.esprit.entity.Feedback;


public interface IFeedback {
	public Feedback addFeedback(Feedback feedback,Long idUSer);
	public void AddAndAffectFeedbackusers(Feedback f , List<Long> idUsers,Long idUs);
	public Feedback retrieveFeedback(Integer idfeedback);
	public Feedback UpdateFeedback(Feedback f, List<Long> usersId);
	public void deleteFeedback(Integer idfeedback);
	public void desaffecterFeedback(Integer idfeedback, Long idUser);
	public List<Feedback> ListAllFeedbackAdmin();
	public Set<Feedback> ListFeedbacksByUser(Long idUser);
	
	
	
	
	
	
	
 
	
	

}
