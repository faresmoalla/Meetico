package tn.esprit.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.entity.Alert;
import tn.esprit.entity.Comment;
import tn.esprit.entity.DictionnaireBadWords;
import tn.esprit.entity.PostDislike;
import tn.esprit.entity.PostLike;
import tn.esprit.entity.Publication;
import tn.esprit.entity.User;
import tn.esprit.repository.AlertRepository;
import tn.esprit.repository.DictionnaireRepository;
import tn.esprit.repository.DislikeRepository;
import tn.esprit.repository.LikeRepository;
import tn.esprit.repository.PublicationRepository;
import tn.esprit.repository.UserRepository;

@Slf4j
@Service
public class PublicationServiceImpl implements IPublicationService {
	@Autowired
	PublicationRepository publicationrepo;
	@Autowired
	UserRepository utiRepo;
	@Autowired
	LikeRepository likeRepo;
	@Autowired
	DislikeRepository dislikeRepo;
	@Autowired
	JavaMailSender javaMailSender;
	@Autowired
	DictionnaireRepository badwordsRepo;
	@Autowired
	PasswordEncoder passwordencoder;
	@Autowired
	AlertRepository alertrepo;
	@Autowired
	CommentServiceImpl commService;

/////////////////////////Ajout Publication//////////////////
	@Override
	public void addPublication(Publication publication, Long idUser) {
		User user = utiRepo.findById(idUser).orElse(null);
		List<String> badwords1 = new ArrayList<String>();
		List<DictionnaireBadWords> badwords = badwordsRepo.findAll();
		Date currentSqlDate = new Date(System.currentTimeMillis());
		publication.setDate(currentSqlDate);
		publication.setUserr(user);

		for (DictionnaireBadWords bd : badwords) {
			badwords1.add(bd.getWord());
		}
		if (verif(publication) == 1) {
			publicationrepo.save(publication);

		} else if (verif(publication) == 0) {
			String encodedPass = passwordencoder.encode(publication.getContents());
			publication.setContents(encodedPass);
			// comment.setContents("*****");
			publicationrepo.save(publication);
			commService.sendsms(user.getTel(), 0);
			Alert a1 = new Alert();
			a1.setUtilis(user);
			alertrepo.save(a1);
			log.warn("Bad  Word");
		}
		if (user.getAlerts().size() >= 3) {
			commService.sendsms2(user.getTel(), 0);
			utiRepo.delete(user);
		}
	}

///////////////////////Delete Publication//////////////////////
	@Override
	public void deletePublication(Long idUser, Long idPublication) {

		User user = utiRepo.findById(idUser).orElse(null);
		Publication publication = publicationrepo.findById(idPublication).orElse(null);
		if (user.getUserId() == publication.getUserr().getUserId()) {
			publicationrepo.deleteById(idPublication);
		} else {
			log.warn("You cant delete this publication");
		}
	}

////////////////////Update Publication//////////////
	@Override
	public void updatePublication(Long idUser, Long idPublication) {

		User user = utiRepo.findById(idUser).orElse(null);
		Publication publication = publicationrepo.findById(idPublication).orElse(null);
		if (user.getUserId() == publication.getUserr().getUserId()) {
			publicationrepo.deleteById(idPublication);
		} else {
			log.warn("You cant delete this publication");
		}
	}

///////////// Like & Dislike//////////
	@Override
	public void addLike(Long idPublicaiton, Long idUser) {
		PostLike lk = new PostLike();
		Publication publication = publicationrepo.findById(idPublicaiton).orElse(null);
		User user = utiRepo.findById(idUser).orElse(null);
		PostLike like = likeRepo.GetLike(idPublicaiton, idUser);
		PostDislike dislike = dislikeRepo.GetDislike(idPublicaiton, idUser);
		lk.setPublication(publication);
		lk.setUtilis(user);
		if (like == null && dislike == null) {
			likeRepo.save(lk);
		}
		else if (like == null && dislike != null) {
			likeRepo.save(lk);
			dislikeRepo.delete(dislike);
		} else {
			likeRepo.delete(like);
		}
	}

	@Override
	public void addDislike(Long idPublicaiton, Long idUser) {
		PostDislike lk = new PostDislike();
		Publication publication = publicationrepo.findById(idPublicaiton).orElse(null);
		User user = utiRepo.findById(idUser).orElse(null);
		PostLike like = likeRepo.GetLike(idPublicaiton, idUser);
		PostDislike dislike = dislikeRepo.GetDislike(idPublicaiton, idUser);
		lk.setPublication(publication);
		lk.setUtilis(user);
		if (like == null && dislike == null) {
			dislikeRepo.save(lk);
		}
		else if (dislike == null && like != null) {
			dislikeRepo.save(lk);
			likeRepo.delete(like);
		} else {
			likeRepo.delete(like);
		}
	}
	
	
	/////////////////////////Nombres Like , Dislikes , Comments////////////////
	@Override
	public int nbrLikeByPub(Long idPublicaiton) {
		Publication publication = publicationrepo.findById(idPublicaiton).orElse(null);
		return publication.getLikes().size();
	}
	@Override
	public int nbrDisLikeByPub(Long idPublicaiton) {
		Publication publication = publicationrepo.findById(idPublicaiton).orElse(null);
		return publication.getDislikes().size();
	}
	@Override
	public int nbrCommentsByPu(Long idPublicaiton) {
		Publication publication = publicationrepo.findById(idPublicaiton).orElse(null);
		return publication.getComments().size();
	}
	
//////////////////////// Stat Best User////////////
	@Override
	public int MeilleurUser() {
		int array[];
		List<User> listuser = utiRepo.findAll();
		List<Integer> listpub = null;
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		for (User user : listuser) {
			arrayList.add(user.getPublications().size());
		}
		return Collections.max(arrayList);
	}
	
	@Override
	public Publication getPub(Long idPublication) {
		Publication publication = publicationrepo.findById(idPublication).orElse(null);
		return publication;
	}

	
	@Override
	public int verif(Publication c) {

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



}
