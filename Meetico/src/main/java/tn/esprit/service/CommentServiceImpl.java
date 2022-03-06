package tn.esprit.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.Module;
import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.entity.Alert;
import tn.esprit.entity.Comment;
import tn.esprit.entity.DictionnaireBadWords;
import tn.esprit.entity.PostLike;
import tn.esprit.entity.Publication;
import tn.esprit.entity.User;
import tn.esprit.repository.AlertRepository;
import tn.esprit.repository.CommentRepository;
import tn.esprit.repository.DictionnaireRepository;
import tn.esprit.repository.PublicationRepository;
import tn.esprit.repository.UserRepository;



@Service
public class CommentServiceImpl {
	@Autowired
	CommentRepository commentRepo;
	@Autowired
	PublicationRepository publciationRepo;
	@Autowired
	DictionnaireRepository badwordsRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	 JavaMailSender javaMailSender;
	@Autowired
	PasswordEncoder passwordencoder;
	@Autowired
	AlertRepository alertrepo;
	
	/*
	private final SmsService smsservice;

	@Autowired
	public CommentServiceImpl(SmsService smsservice) {
	    this.smsservice = smsservice;
	}*/
	
	
	/////////////// Partie Back Admin////////
	
	public List<Comment> ListAllCommentsAdmin(String field) {
		//return commentRepo.findAll();
		return commentRepo.findAll(Sort.by(Sort.Direction.ASC,field));
	}
	
	
	
	
	
	
	
	
	
	//////////////////////Partie Front Client/////////////////
	
	public int verif(Comment c) {

		for (DictionnaireBadWords d : badwordsRepo.findAll()) {

			if (c.getContents().toLowerCase().contains(d.getWord().toLowerCase()) || c.getContents()==null || c.getContents().length() == 0) {
				return 0;
			}
			else {
				return 1;
			}
			
		}  return 2;
		
	}
	
	
	public void sendsms(String str, int body) {
	    Twilio.init("AC1031db4af6517ccd09f33ef47e73e278", "cf4632728e95b7aedd1b953468ce63b4");
	    try {
	     com.twilio.rest.api.v2010.account.Message message = com.twilio.rest.api.v2010.account.Message.creator(
	     		new PhoneNumber("+21692207710"), // To number
	             new PhoneNumber("+16066136706"), // From number
	             "This is an Alert you send a bad word :"+body
	     ).create();
	    }catch (Exception e) {
	 	// TODO: handle exception
	 }
		
	}
	
	public void sendsms2(String str, int body) {
	    Twilio.init("AC1031db4af6517ccd09f33ef47e73e278", "cf4632728e95b7aedd1b953468ce63b4");
	    try {
	     com.twilio.rest.api.v2010.account.Message message = com.twilio.rest.api.v2010.account.Message.creator(
	     		new PhoneNumber("+21692207710"), // To number
	             new PhoneNumber("+16066136706"), // From number
	             "You have been banned for saying too much  bad words :"+body
	     ).create();
	    }catch (Exception e) {
	 	// TODO: handle exception
	 }
		
	}
	
	

	public void addcomments(Comment comment , Long idpub,Long idUser ) {
	
	Publication  pub=publciationRepo.findById(idpub).orElse(null);	
	User  user=userRepo.findById(idUser).orElse(null);
		String commentWords = comment.getContents();
		List<String> badwords1 = new ArrayList<String>();
		Date currentSqlDate = new Date(System.currentTimeMillis());
		comment.setDate(currentSqlDate);
		comment.setPublications(pub);
		comment.setUser(user);
		List<DictionnaireBadWords> badwords = badwordsRepo.findAll();
		
		for (DictionnaireBadWords bd : badwords) {
			badwords1.add(bd.getWord());
		}
		
		if (verif(comment)==1) {	


			commentRepo.save(comment);

		}
		
		else if(verif(comment)==0) {
			
			String encodedPass =  passwordencoder.encode(comment.getContents());
			comment.setContents(encodedPass);
			//comment.setContents("*****");
			commentRepo.save(comment);

			sendsms(user.getTel(),0);
		
			Alert a1 = new Alert();
			a1.setUtilis(user);
			alertrepo.save(a1);
			

		}
		
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		
		simpleMailMessage.setFrom(pub.getUserr().getEmail());
		simpleMailMessage.setTo(user.getEmail());
		simpleMailMessage.setSubject(user.getFirstName()+" "+"a commenté votre publication");
		simpleMailMessage.setText(comment.getContents());

		javaMailSender.send(simpleMailMessage);
			

if(user.getAlerts().size()>=3) {
	
	sendsms2(user.getTel(),0);
	userRepo.delete(user);
	

}

}

	
	
	// a corriger

	public void updateComment(Long idComment, Long idpub,Long idUser ) {
		Comment  com=commentRepo.findById(idComment).orElse(null);
		Publication  pub=publciationRepo.findById(idpub).orElse(null);	
		User  user=userRepo.findById(idUser).orElse(null);
		
	
		
		Date currentSqlDate = new Date(System.currentTimeMillis());
		
		com.setDate(currentSqlDate);
		com.setPublications(pub);
		com.setUser(user);
		
		if (  (verif(com))  ==0) {	
			commentRepo.save(com);
		} else {
			com.setContents("*****");
			commentRepo.save(com);

		}

	}
	

	public void deleteComment(Long idComment) {
		commentRepo.deleteById(idComment);
	}


	public List<Comment> listCommentsByPublication(Long idPublicaiton) {
		Publication publication = publciationRepo.findById(idPublicaiton).orElse(null);

		List<Comment> listComments = commentRepo.listcommentsByPublication(idPublicaiton);

		return listComments;
	}

}
