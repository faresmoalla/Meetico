package tn.esprit.service;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import tn.esprit.entity.Feedback;
import tn.esprit.entity.Trip;
import tn.esprit.entity.User;
import tn.esprit.repository.TripRepository;
import tn.esprit.repository.UserRepository;
import tn.esprit.repository.feedbackRepository;

@Service
@Slf4j
public class serviceFeedback implements IFeedback {
	
	@Autowired
	feedbackRepository feedbackrepository; 
	@Autowired
	UserRepository userrepository;
	@Autowired
	TripRepository tripRepository;
	
	@Override
	@Transactional
	public void AddAndAffectFeedbackUsersTrip(Feedback feed , List<Long> idUsers, Long idUs,Integer idTrip) {
		Feedback f =addFeedback(feed, idUs,idTrip);
		
		for (Long idUser : idUsers) {
			User U = userrepository.findById(idUser).orElse(null);
			U.getFeedbacks().add(f);
			userrepository.save(U);
		}
		
	}
		@Override
		public Feedback addFeedback(Feedback feedback, Long idUSer,Integer idTrip) {
			User u = userrepository.findById(idUSer).orElse(null);
			Trip t= tripRepository.findById(idTrip).orElse(null);
			feedback.setUser(u);
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
		
}
