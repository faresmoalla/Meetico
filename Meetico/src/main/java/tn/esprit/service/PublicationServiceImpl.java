package tn.esprit.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import tn.esprit.entity.Comment;
import tn.esprit.entity.DictionnaireBadWords;
import tn.esprit.entity.PostDislike;
import tn.esprit.entity.PostLike;
import tn.esprit.entity.Publication;
import tn.esprit.entity.User;

import tn.esprit.repository.DislikeRepository;
import tn.esprit.repository.LikeRepository;
import tn.esprit.repository.PublicationRepository;
import tn.esprit.repository.UserRepository;



@Service
public class PublicationServiceImpl implements IPublicationService {
@Autowired
PublicationRepository publicationrepo ;
@Autowired
UserRepository utiRepo ;
@Autowired
LikeRepository likeRepo;
@Autowired
DislikeRepository dislikeRepo;
@Autowired
 JavaMailSender javaMailSender;





public Publication getPub(Long idPublication) {
	Publication  publication=publicationrepo.findById(idPublication).orElse(null);	
	return publication;
}

@Override
public void addPublication(Publication publication,Long idUser) {

User  user=utiRepo.findById(idUser).orElse(null);
	
	
	Date currentSqlDate = new Date(System.currentTimeMillis());
	publication.setDate(currentSqlDate);
	
	publication.setUserr(user);

	
	
		publicationrepo.save(publication);

		//System.out.println("sending email ...");
/*
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		
		simpleMailMessage.setFrom("9ariniphoenix@gmail.com");
		simpleMailMessage.setTo("fares.moalla@esprit.tn");
		simpleMailMessage.setSubject("test");
		simpleMailMessage.setText("test" );

		javaMailSender.send(simpleMailMessage);*/
		//System.out.println("sent email ...");
		
		
		

}




public void deletePublication(Long idPublication) {
	publicationrepo.deleteById(idPublication);	
}


public void addLike(Long idPublicaiton,Long idUser){
PostLike lk =    new PostLike();	
	Publication  publication=publicationrepo.findById(idPublicaiton).orElse(null);	
	User  user=utiRepo.findById(idUser).orElse(null);	
	
	PostLike  like=likeRepo.GetLike(idPublicaiton,idUser);	
	PostDislike  dislike=dislikeRepo.GetDislike(idPublicaiton,idUser);
	lk.setPublication(publication);
	lk.setUtilis(user);

	if(like==null && dislike==null) {
		
		
		
		likeRepo.save(lk);	
		
		
	}
	
	else if(like==null && dislike!=null)  {
		likeRepo.save(lk);
		dislikeRepo.delete(dislike);
	
	}
	else{
		
		
		likeRepo.delete(like);
		
	}
	
		
	
}

public void addDislike(Long idPublicaiton,Long idUser){
PostDislike lk =    new PostDislike();	
	Publication  publication=publicationrepo.findById(idPublicaiton).orElse(null);	
	User  user=utiRepo.findById(idUser).orElse(null);	
	
	PostLike  like=likeRepo.GetLike(idPublicaiton,idUser);	
	PostDislike  dislike=dislikeRepo.GetDislike(idPublicaiton,idUser);
	lk.setPublication(publication);
	lk.setUtilis(user);

	if(like==null && dislike==null) {
		
		
		
		dislikeRepo.save(lk);	
		
		
	}
	
	else if(dislike==null && like!=null)  {
		dislikeRepo.save(lk);
		likeRepo.delete(like);
	
	}
	else{
		
		
		likeRepo.delete(like);
		
	}
	
		
	
}


	





public int nbrLikeByPub(Long idPublicaiton) {
	Publication  publication=publicationrepo.findById(idPublicaiton).orElse(null);	
return	 publication.getLikes().size();
}

public int nbrDisLikeByPub(Long idPublicaiton) {
	Publication  publication=publicationrepo.findById(idPublicaiton).orElse(null);	
return	 publication.getDislikes().size();
}   


public int nbrCommentsByPu(Long idPublicaiton) {
	Publication  publication=publicationrepo.findById(idPublicaiton).orElse(null);		
	return	 publication.getComments().size();	
}















}
