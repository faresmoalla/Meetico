package tn.esprit.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.Module;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.entity.Comment;
import tn.esprit.entity.DictionnaireBadWords;
import tn.esprit.entity.PostLike;
import tn.esprit.entity.Publication;
import tn.esprit.entity.User;
import tn.esprit.repository.CommentRepository;
import tn.esprit.repository.DictionnaireRepository;
import tn.esprit.repository.PublicationRepository;
import tn.esprit.repository.UserRepository;



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
	
	/////////////// Partie Back Admin////////
	@Override
	public List<Comment> ListAllCommentsAdmin(Long idPublicaiton) {
		return commentRepo.findAll();
	}
	
	
	
	
	
	
	
	
	
	//////////////////////Partie Front Client/////////////////
	@Override
	public boolean verif(Comment c) {

		for (DictionnaireBadWords d : badwordsRepo.findAll()) {

			if (c.getContents().toLowerCase().contains(d.getWord().toLowerCase()) &&c.getContents()==null && c.getContents().length() == 0) {
				return false;
			}

		}
		return true;
	}
	
	
	
@Override
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
		if (!verif(comment)) {	


			
			//comment.setContents(encodedPass);
			commentRepo.save(comment);
			
			/*SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			
			simpleMailMessage.setFrom(pub.getUserr().getEmail());
			simpleMailMessage.setTo(user.getEmail());
			simpleMailMessage.setSubject(user.getFirstName()+" "+"a commenté votre publication");
			simpleMailMessage.setText(comment.getContents());

			javaMailSender.send(simpleMailMessage);
			*/
			
			
		} else {
			
			String encodedPass =  passwordencoder.encode(comment.getContents());
			comment.setContents(encodedPass);
			//comment.setContents("*****");
			commentRepo.save(comment);

		}

	}

	
	
	// a corriger
@Override
	public void updateComment(Long idComment, Long idpub,Long idUser ) {
		Comment  com=commentRepo.findById(idComment).orElse(null);
		Publication  pub=publciationRepo.findById(idpub).orElse(null);	
		User  user=userRepo.findById(idUser).orElse(null);
		
	
		
		Date currentSqlDate = new Date(System.currentTimeMillis());
		
		com.setDate(currentSqlDate);
		com.setPublications(pub);
		com.setUser(user);
		
		if (verif(com)) {	
			commentRepo.save(com);
		} else {
			com.setContents("*****");
			commentRepo.save(com);

		}

	}
	
@Override
	public void deleteComment(Long idComment) {
		commentRepo.deleteById(idComment);
	}

@Override
	public List<Comment> listCommentsByPublication(Long idPublicaiton) {
		Publication publication = publciationRepo.findById(idPublicaiton).orElse(null);

		List<Comment> listComments = commentRepo.listcommentsByPublication(idPublicaiton);

		return listComments;
	}

}
