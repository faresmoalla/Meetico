package tn.esprit.service;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import tn.esprit.entity.Feedback;
import tn.esprit.entity.User;
import tn.esprit.repository.UserRepository;
import tn.esprit.repository.feedbackRepository;

@Service
@Slf4j
public class serviceFeedback implements IFeedback {
	
	@Autowired
	feedbackRepository feedbackrepository; 
	@Autowired
	UserRepository userrepository;
	
	@Override
	@Transactional
	public void AddAndAffectFeedbackusers(Feedback feed , List<Long> idUsers, Long idUs) {
		Feedback f =addFeedback(feed, idUs);
		for (Long idUser : idUsers) {
			User U = userrepository.findById(idUser).orElse(null);
			U.getFeedbacks().add(f);
			userrepository.save(U);
		}
		
	}
		@Override
		public Feedback addFeedback(Feedback feedback, Long idUSer) {
			User u = userrepository.findById(idUSer).orElse(null);
			feedback.setUser(u);
			return feedbackrepository.save(feedback);
		}
		

}
