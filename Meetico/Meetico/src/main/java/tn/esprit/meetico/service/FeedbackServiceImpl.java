package tn.esprit.meetico.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.meetico.entity.Feedback;
import tn.esprit.meetico.entity.Trip;
import tn.esprit.meetico.entity.User;
import tn.esprit.meetico.repository.TripRepository;
import tn.esprit.meetico.repository.UserRepository;
import tn.esprit.meetico.repository.FeedbackRepository;

@Service
public class FeedbackServiceImpl implements IFeedbackService {
	
	@Autowired
	FeedbackRepository feedbackrepository; 
	@Autowired
	UserRepository userrepository;
	@Autowired
	TripRepository tripRepository;
	
	@Override
	@Transactional
	public void AddAndAffectFeedbackUsersTrip(Feedback feed , List<Long> idUsers, User user,Integer idTrip) {
		Feedback f =addFeedback(feed, user,idTrip);
		
		for (Long idUser : idUsers) {
			User U = userrepository.findById(idUser).orElse(null);
			U.getFeedbacks().add(f);
			userrepository.save(U);
		}
		
	}
		@Override
		public Feedback addFeedback(Feedback feedback, User user,Integer idTrip) {
			
			//User u = userrepository.findById(idUSer).orElse(null);
			Trip t= tripRepository.findById(idTrip).orElse(null);
			feedback.setUser(user);
			feedback.setTrip(t);
			return feedbackrepository.save(feedback);
		}
		@Override
		public Feedback retrieveFeedback(Integer idfeedback) {
			return feedbackrepository.findById(idfeedback).orElse(null);
		}
		
		@Override
		public Feedback UpdateFeedback(Feedback f, List<Long> usersId) {
		Feedback feedback = retrieveFeedback(f.getIdFeedback());
		feedback.setDescription(f.getDescription());
		feedback.setLastModificationDate(f.getLastModificationDate());
		feedback.setStars(f.getStars());
		
		for (Long idUser : usersId) {
			User U = userrepository.findById(idUser).orElse(null);
			U.getFeedbacks().add(feedback);
			userrepository.save(U);
		}
		return feedbackrepository.save(feedback);
	}
		@Override
		public void deleteFeedback(Integer idfeedback) {
			Feedback f = retrieveFeedback(idfeedback);
			for(User u : f.getUsers()) {
				u.setFeedbacks(null);
				f.setUsers(null);
				
			}
			feedbackrepository.deleteById(idfeedback);
			
		}
		@Override
		public void desaffecterFeedback(Integer idfeedback, Long idUser) {
			Feedback f = retrieveFeedback(idfeedback);
			
			for(User u : f.getUsers()) {
				
			if(u.getUserId() == idUser) {
					u.setFeedbacks(null);
					f.setUsers(null);
				}
			}
			feedbackrepository.save(f);
			
		}
		@Override
		public List<Feedback> ListAllFeedbackAdmin() {
			
			return feedbackrepository.findAll();
		}
		@Override
		public Set<Feedback> ListFeedbacksByUser(Long idUser) {
		
			return feedbackrepository.getAllFeedbacksClient(idUser);
		}
		
		@Override
		public Set<Feedback> ListFeedbacksByTAG(Long idUser) {
			
			return feedbackrepository.getFeedbacksClientTAG(idUser);
			
		}
		@Override
		public List<Float> StatFeedbacksBystars(User user) {
			float d=0;
			List<Float> statTotale = new ArrayList();
			Integer sss;
			//Integer nbrFeedback =user.getFeedbacks().size();
			Integer nbrFeedback= feedbackrepository.nbrFeedbacks(user.getUserId());
			for(Integer star=1; star<=5; star++) {
				sss=feedbackrepository.nbrFeedbackbystars(star);
				System.out.println("//////////////////////"+sss+"//////////////////////");
				d=sss*100/nbrFeedback;
				System.out.println("****************"+nbrFeedback+"********************");
				statTotale.add(d);
				sss=0;
				d=0;
			}
			
			return statTotale;
		}
	
}
		
	
		

