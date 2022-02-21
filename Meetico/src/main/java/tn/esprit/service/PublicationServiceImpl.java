package tn.esprit.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import tn.esprit.entities.Comment;
import tn.esprit.entities.PostDislike;
import tn.esprit.entities.PostLike;
import tn.esprit.entities.Publication;
import tn.esprit.entities.Utilisateur;

import tn.esprit.repository.DislikeRepository;
import tn.esprit.repository.LikeRepository;
import tn.esprit.repository.PublicationRepository;
import tn.esprit.repository.UtilisateurRepository;


@Service
public class PublicationServiceImpl implements IPublicationService {
@Autowired
PublicationRepository publicationrepo ;
@Autowired
UtilisateurRepository utiRepo ;
@Autowired
LikeRepository likeRepo;
@Autowired
DislikeRepository dislikeRepo;



public void addPublication(Publication publication) {
	publicationrepo.save(publication);
}

public void deletePublication(Long idPublication) {
	publicationrepo.deleteById(idPublication);	
}





/*
public void createLike(Long postId, Long idUser) {
	Publication  publication=publicationrepo.findById(postId).orElse(null);	
	Utilisateur  user=utiRepo.findById(idUser).orElse(null);
    
	
	publication.getLikes().add(user);
    
    publicationrepo.save(publication);
}
*/








public void addLike(Long idPublicaiton,Long idUser){
PostLike lk =    new PostLike();	
	Publication  publication=publicationrepo.findById(idPublicaiton).orElse(null);	
	Utilisateur  user=utiRepo.findById(idUser).orElse(null);	
	lk.setPublication(publication);
	lk.setUtilis(user);

	
	
	likeRepo.save(lk);
		
	
}


	



public void addDisLike(Long idPublicaiton,Long idUser){
PostDislike lk =    new PostDislike();	
	Publication  publication=publicationrepo.findById(idPublicaiton).orElse(null);	
	Utilisateur  user=utiRepo.findById(idUser).orElse(null);	
	lk.setPublication(publication);
	lk.setUtilis(user);
	dislikeRepo.save(lk);
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
