package tn.esprit.service;

import java.util.List;
import java.util.Set;

import tn.esprit.entity.Feedback;


public interface IFeedback {
	public Feedback addFeedback(Feedback feedback,Long idUSer);
	public void AddAndAffectFeedbackusers(Feedback f , List<Long> idUsers,Long idUs);
	
	
	
	
	
	
	
	
	
	
	
 
	
	

}
