package tn.esprit.meetico.service;

import java.util.List;
import java.util.Set;

import tn.esprit.meetico.entity.Feedback;
import tn.esprit.meetico.entity.User;


public interface IFeedbackService {
	public Feedback addFeedback(Feedback feedback,User user,Integer idTrip);
	public void AddAndAffectFeedbackUsersTrip(Feedback feed , List<Long> idUsers, User user,Integer idTrip) ; 
	public Feedback retrieveFeedback(Integer idfeedback);
	public Feedback UpdateFeedback(Feedback f, List<Long> usersId);
	public void deleteFeedback(Integer idfeedback);
	public void desaffecterFeedback(Integer idfeedback, Long idUser);
	public List<Feedback> ListAllFeedbackAdmin();
	public Set<Feedback> ListFeedbacksByUser(Long idUser);
	public Set<Feedback> ListFeedbacksByTAG(Long idUser);
	public List<Float> StatFeedbacksBystars(User user);
	public void StatFeedbacksBystars1(User user);
}
