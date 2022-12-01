package tn.esprit.meetico.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
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
import tn.esprit.meetico.entity.Alert;
import tn.esprit.meetico.entity.Comment;
import tn.esprit.meetico.entity.DictionnaireBadWords;
import tn.esprit.meetico.entity.PostLike;
import tn.esprit.meetico.entity.Publication;
import tn.esprit.meetico.entity.User;
import tn.esprit.meetico.repository.AlertRepository;
import tn.esprit.meetico.repository.CommentRepository;
import tn.esprit.meetico.repository.DictionnaireRepository;
import tn.esprit.meetico.repository.PublicationRepository;
import tn.esprit.meetico.repository.UserRepository;
import org.springframework.data.domain.PageRequest;

@Slf4j
@Service
public class CommentServiceImpl implements ICommentService {
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



	/////////////// Partie Back Admin////////
	@Override
	public List<Comment> ListAllCommentsAdmin(String field) {
		// return commentRepo.findAll();
		return commentRepo.findAll(Sort.by(Sort.Direction.ASC, field));
	}


	@Override
	public Page<Comment> ListAllCommentsAdminPaginator(int offset, int pageSize) {
		Page<Comment> listcomments = commentRepo.findAll(PageRequest.of(offset, pageSize));
		// return commentRepo.findAll();
		return listcomments;
	}

	////////////////////// Partie Front Client/////////////////

	////////////////////// Ajouter Commentaire
	@Override
	public void addcomments(Comment comment, Long idpub, User user) {
		Publication pub = publciationRepo.findById(idpub).orElse(null);
		
	//	User user = userRepo.findById(idUser).orElse(null);
		
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
		if (verif(comment) == 1) {
			commentRepo.save(comment);
		} else if (verif(comment) == 0) {
			
			String encodedPass = passwordencoder.encode(comment.getContents());
			comment.setContents(encodedPass);
			// comment.setContents("*****");
			commentRepo.save(comment);
			sendsms(user.getTel(), 0);
			Alert a1 = new Alert();
			a1.setUtilis(user);
			alertrepo.save(a1);
			log.warn("Bad  Word");
		}
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(pub.getUserr().getEmail());
		simpleMailMessage.setTo(user.getEmail());
		simpleMailMessage.setSubject(user.getFirstName() + " " + "a commenté votre publication");
		simpleMailMessage.setText(comment.getContents());
		javaMailSender.send(simpleMailMessage);
		/*
		if (user.getAlerts().size() >= 3) {
			sendsms2(user.getTel(), 0);
			userRepo.delete(user);
		}*/
		
		
	}
	
	
	
	
	public void testaddComment(Comment comment) {
		Date currentSqlDate = new Date(System.currentTimeMillis());
		comment.setDate(currentSqlDate);
		commentRepo.save(comment);
	}

	///////////////////// Modifier Commentaire ///////////////
	@Override
	public void updateComment(Comment comment, Long idComment, User user) {
		Date currentSqlDate = new Date(System.currentTimeMillis());
		Comment com = commentRepo.findById(idComment).orElse(null);
		//User user = userRepo.findById(idUser).orElse(null);
		com.setIdComment(idComment);
		com.setDate(currentSqlDate);
		com.setUser(user);
		com.setContents(comment.getContents());
		List<String> badwords1 = new ArrayList<String>();
		List<DictionnaireBadWords> badwords = badwordsRepo.findAll();
		for (DictionnaireBadWords bd : badwords) {
			badwords1.add(bd.getWord());
		}
		if (verif(com) == 1) {
			commentRepo.save(com);
		} else if (verif(com) == 0) {
			String encodedPass = passwordencoder.encode(comment.getContents());
			com.setContents(encodedPass);

			commentRepo.save(com);
			sendsms(user.getTel(), 0);
			Alert a1 = new Alert();
			a1.setUtilis(user);
			alertrepo.save(a1);
			log.warn("Bad  Word");
		}
		if (user.getAlerts().size() >= 3) {
			sendsms2(user.getTel(), 0);
			userRepo.delete(user);
		}
	}

	//////////////////////////// Delete Comment
	@Override
	public void deleteComment(Long idComment) {
		Comment comment = commentRepo.findById(idComment).orElse(null);
		
		comment.setUser(null);
		commentRepo.delete(comment);
		
	}

	////////////////////////// Afficher les Commentaire d'une Publication
	@Override
	public List<Comment> listCommentsByPublication(Long idPublicaiton) {
		Publication publication = publciationRepo.findById(idPublicaiton).orElse(null);

		List<Comment> listComments = commentRepo.listcommentsByPublication(idPublicaiton);

		return listComments;
	}

	@Override
	public int verif(Comment c) {

		for (DictionnaireBadWords d : badwordsRepo.findAll()) {

			if (c.getContents().toLowerCase().contains(d.getWord().toLowerCase()) || c.getContents() == null
					|| c.getContents().length() == 0) {
				return 0;
			} else {
				return 1;
			}

		}
		return 2;

	}

	//////////////////////////// Api et métiers/////////////
	@Override
	public void sendsms(String str, int body) {
		Twilio.init("AC1031db4af6517ccd09f33ef47e73e278", "cf4632728e95b7aedd1b953468ce63b4");
		try {
			com.twilio.rest.api.v2010.account.Message message = com.twilio.rest.api.v2010.account.Message
					.creator(new PhoneNumber("+21692207710"), // To number
							new PhoneNumber("+16066136706"), // From number
							"This is an Alert you send a bad word :" + body)
					.create();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	public void sendsms3(String str, int body) {
		Twilio.init("AC1031db4af6517ccd09f33ef47e73e278", "cf4632728e95b7aedd1b953468ce63b4");
		try {
			com.twilio.rest.api.v2010.account.Message message = com.twilio.rest.api.v2010.account.Message
					.creator(new PhoneNumber("+21692207710"), // To number
							new PhoneNumber("+16066136706"), // From number
							"you are not banned anymore welcome back:" + body)
					.create();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	
	@Override
	public void sendsms2(String str, int body) {
		Twilio.init("AC1031db4af6517ccd09f33ef47e73e278", "cf4632728e95b7aedd1b953468ce63b4");
		try {
			com.twilio.rest.api.v2010.account.Message message = com.twilio.rest.api.v2010.account.Message
					.creator(new PhoneNumber("+21692207710"), // To number
							new PhoneNumber("+16066136706"), // From number
							"You have been banned for saying too much  bad words :" + body)
					.create();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}


	@Override
	public List<Comment> ListAllCommentsAdmin(Long idPublicaiton, String field) {
		// TODO Auto-generated method stub
		return null;
	}

}
