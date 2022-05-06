package tn.esprit.meetico.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;



import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import tn.esprit.meetico.entity.Alert;
import tn.esprit.meetico.entity.BannedUser;
import tn.esprit.meetico.entity.Comment;
import tn.esprit.meetico.entity.DictionnaireBadWords;
import tn.esprit.meetico.entity.PostDislike;
import tn.esprit.meetico.entity.PostLike;
import tn.esprit.meetico.entity.Publication;
import tn.esprit.meetico.entity.User;
import tn.esprit.meetico.repository.AlertRepository;

import tn.esprit.meetico.repository.BannedUserRepository;
import tn.esprit.meetico.repository.DictionnaireRepository;
import tn.esprit.meetico.repository.DislikeRepository;
import tn.esprit.meetico.repository.LikeRepository;
import tn.esprit.meetico.repository.PublicationRepository;
import tn.esprit.meetico.repository.UserRepository;

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
//	@Autowired
//	JobLauncher jobLauncher;
//
//	@Autowired
//	Job job;

	
	/*
		public int MeilleurUser() {
		
		int array[];
		List<User> listuser = utiRepo.findAll();
		List<Integer> listpub = null;
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		for (User user : listuser) {
			arrayList.add(user.getPublications().size());
		}
		log.info("L'utulisateur le plus actif qui a " +Collections.max(arrayList)+" " + "publications");
		return Collections.max(arrayList);
	}
	*/
	public User GetBestUser() {
List<User> users =userREpo.findAll();
		
		String s= new String();
		List<Integer> ns=new ArrayList<>();
		User user =new User();
		int max_value = 0;
		for(User u:users) {
			int n = 0 ;
			for(User ur:users) {
				if(u.getPublications().size()>ur.getPublications().size()) {
					n++;
				}
				ns.add(n);
				max_value= Collections.max(ns);
				if(n==max_value) {
					user=u;
					}
				
			};
				
			
			//s =t.getDestination()+"est visité"+n+"fois"+max_value;
			//ls.add(s);
		}
//		s="The favorite destination was " + destination + " with " + max_value + " visit(s).";
//		
//		log.info(s);
		
		return user;
		
	}
	
	
	
	
	
	
	
/////////////////////////Ajout Publication//////////////////
	@Override
	public Publication addPublication(Publication publication, User user) {
		//User user = utiRepo.findById(idUser).orElse(null);
		List<String> badwords1 = new ArrayList<String>();
		List<DictionnaireBadWords> badwords = badwordsRepo.findAll();
		Date currentSqlDate = new Date(System.currentTimeMillis());
		publication.setDate(currentSqlDate);
		publication.setUserr(user);
		
		

//		for (DictionnaireBadWords bd : badwords) {
//			badwords1.add(bd.getWord());
//		}
//		if (verif(publication) == 1) {
//			publicationrepo.save(publication);
//
//		} else if (verif(publication) == 0) {
//			String encodedPass = passwordencoder.encode(publication.getContents());
//			publication.setContents(encodedPass);
//			
//			publicationrepo.save(publication);
//			commService.sendsms(user.getTel(), 0);
//		
//		}
		if (verif(publication) == 0) {
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setFrom(publication.getUserr().getEmail());
			simpleMailMessage.setTo(user.getEmail());
			simpleMailMessage.setSubject("You have said a bad word this is an alert if you keep saying more bad words you will get banned");
			simpleMailMessage.setText(publication.getContents());
			javaMailSender.send(simpleMailMessage);
				
								
		}

		return publicationrepo.save(publication); 
		
		
		
	}

	
	
	
///////////////////////Delete Publication//////////////////////
	
	public void deletePublication(/*Long idUser,*/ Long idPublication) {

		//User user = utiRepo.findById(idUser).orElse(null);
		Publication publication = publicationrepo.findById(idPublication).orElse(null);
		publication.setUserr(null);
		publicationrepo.delete(publication);
	
		
	}
	public void deleteAlert(/*Long idUser,*/ Long idAlert) {

		//User user = utiRepo.findById(idUser).orElse(null);
		Alert alert = alertrepo.findById(idAlert).orElse(null);
		
			alertrepo.deleteById(idAlert);
	
		
	}
////////////////////Update Publication//////////////
	
	public void deletePublication2(Long idUser, Long idPublication) {

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
	public void addLike(Long idPublicaiton,User user) {
		PostLike lk = new PostLike();
		Publication publication = publicationrepo.findById(idPublicaiton).orElse(null);
		lk.setPublication(publication);
		lk.setUtilis(user);
	
		
		//User user = utiRepo.findById(idUser).orElse(null);
		List<PostLike> listlikes = likeRepo.findAll();
		List<PostDislike> listdislikes = dislikeRepo.findAll();
		PostLike like = new PostLike();
		PostDislike dislike = new PostDislike();
		//PostDislike dislike = dislikeRepo.GetDislike(idPublicaiton,user.getUserId());
		for(PostLike l : listlikes) {
			if (l.getPublication().getUserr().getUserId()==l.getUtilis().getUserId()) {
				like=l;
			}
			else {
				like = null;
			}
		}
		
		log.info("hedhi like"+like.getIdLike());
		for(PostDislike dl : listdislikes) {
			if (dl.getPublication().getUserr().getUserId()==dl.getUtilis().getUserId()) {
				dislike=dl;
			}
			else {
				dislike = null;
			}
		}
		log.info("hedhi dislike"+dislike.getIdDislike());
		//PostLike like = publicationrepo.findById(idPublicaiton).orElse(null);

		//PostLike like = likeRepo.GetLike(idPublicaiton, user.getUserId());
		
		//likeRepo.save(lk);
		
		if (like.getIdLike() == null && dislike.getIdDislike() == null) {
			likeRepo.save(lk);
		}
		else if (like.getIdLike() == null && dislike.getIdDislike() != null) {
			likeRepo.save(lk);
			dislikeRepo.delete(dislike);
		} else {
			likeRepo.delete(like);
		}
	}
	
	

	@Override
	public void addDislike(Long idPublicaiton, User user) {
		PostDislike lk = new PostDislike();
		Publication publication = publicationrepo.findById(idPublicaiton).orElse(null);
		//User user = utiRepo.findById(idUser).orElse(null);
		//PostLike like = likeRepo.GetLike(idPublicaiton, user.getUserId());
		//PostDislike dislike = dislikeRepo.GetDislike(idPublicaiton, user.getUserId());
		PostLike like = new PostLike();
		PostDislike dislike = new PostDislike();
		lk.setPublication(publication);
		lk.setUtilis(user);
		
		List<PostLike> listlikes = likeRepo.findAll();
		List<PostDislike> listdislikes = dislikeRepo.findAll();
		
		for(PostLike l : listlikes) {
			if (l.getPublication().getUserr().getUserId()==l.getUtilis().getUserId()) {
				like=l;
			}
			else {
				like = null;
			}
		}
		
		log.info("hedhi like"+like.getIdLike());
		for(PostDislike dl : listdislikes) {
			if (dl.getPublication().getUserr().getUserId()==dl.getUtilis().getUserId()) {
				dislike=dl;
			}
			else {
				dislike = null;
			}
		}
		
		
		
		
		
		if (like.getIdLike() == null && dislike.getIdDislike() == null) {
			dislikeRepo.save(lk);
		}
		else if (dislike.getIdDislike() == null && like.getIdLike() != null) {
			dislikeRepo.save(lk);
			likeRepo.delete(like);
		}
		else {
			dislikeRepo.delete(dislike);
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
	
	@Scheduled(cron = "*/15 * * * * *")
	@Override
	public int MeilleurUser() {
		
		int array[];
		List<User> listuser = utiRepo.findAll();
		List<Integer> listpub = null;
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		for (User user : listuser) {
			arrayList.add(user.getPublications().size());
		}
		log.info("L'utulisateur le plus actif qui a " +Collections.max(arrayList)+" " + "publications");
		return Collections.max(arrayList);
	}
	
	@Override
	public Publication getPub(Long idPublication) {
		Publication publication = publicationrepo.findById(idPublication).orElse(null);
		return publication;
	}
	
	
public int nombreAlerts(User user) {
	return user.getAlerts().size();	
	}


public int nombreAlertBan(User user) {
	int nombre;
	nombre= 10- user.getAlerts().size();	
	
	
	
	return nombre;
}

	

@Autowired
UserRepository userREpo;
public void debloquerUser(Long idUser){
	BannedUser buser = banneduserrepo.findById(idUser).orElse(null);
	User user=new User();
	user.setActive(buser.getActive());
	user.setAddress(buser.getAddress());
	user.setBirthday(buser.getBirthday());
	user.setLastName(buser.getLastName());
	user.setCity(buser.getCity());
	user.setFirstName(buser.getFirstName());
	user.setTel(buser.getTel());
	user.setPhoneNumber(buser.getPhoneNumber());
	user.setEmail(buser.getEmail());
	user.setGender(buser.getGender());
	user.setPassword(buser.getPassword());
	user.setPicturePath(buser.getPicturePath());
	user.setRole(buser.getRole());
	user.setUsername(buser.getUsername());
	
	userREpo.save(user);
	commService.sendsms3(user.getTel(), 0);
	banneduserrepo.delete(buser);
	
	
}

	

public int PublicationOwner(Long idPublication , User user) {
	Publication publication = publicationrepo.findById(idPublication).orElse(null);
	int	etatuser = 0;
	
	if (publication.getUserr().getUserId()==user.getUserId()   ) {
		etatuser=1;
		
	}
	else {
		etatuser=0;
	}
	return etatuser;	
}



@Autowired
BannedUserRepository banneduserrepo;
public boolean badWord(String c, User user) {
	boolean	etat = false;
	BannedUser banneduser =new BannedUser();
	
	banneduser.setActive(user.getActive());
	banneduser.setAddress(user.getAddress());
	banneduser.setBirthday(user.getBirthday());
	banneduser.setLastName(user.getLastName());
	banneduser.setCity(user.getCity());
	banneduser.setFirstName(user.getFirstName());
	banneduser.setTel(user.getTel());
	banneduser.setPhoneNumber(user.getPhoneNumber());
	banneduser.setEmail(user.getEmail());
	banneduser.setGender(user.getGender());
	banneduser.setPassword(user.getPassword());
	banneduser.setPicturePath(user.getPicturePath());
	banneduser.setRole(user.getRole());
	banneduser.setUsername(user.getUsername());
	
	Date currentSqlDate = new Date(System.currentTimeMillis());
	banneduser.setBannedDate(currentSqlDate);
		for (DictionnaireBadWords d : badwordsRepo.findAll()) {

			if (c.toLowerCase().contains(d.getWord().toLowerCase()) || c == null
					|| c.length() == 0) {
				etat = true;
				Alert a1 = new Alert();
				a1.setUtilis(user);
				alertrepo.save(a1);
				commService.sendsms(user.getTel(), 0);
				
				
					
						
			}
			
			else {
				etat = false;
				
			}

		}
		
		
		if (user.getAlerts().size() >= 10) {
			commService.sendsms2(user.getTel(), 0);
			banneduserrepo.save(banneduser);
		utiRepo.delete(user);
	}
		return etat;
		
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
	public Publication retrievePublication(Long idPublication) {

	 return	 publicationrepo.findById(idPublication).orElse(null);
	}
	
	
	
	public void updatePublication(Publication publication, User user) {
		Date currentSqlDate = new Date(System.currentTimeMillis());
		Publication pub = retrievePublication(publication.getIdPublication());
		//User user = userRepo.findById(idUser).orElse(null);
		//pub.setIdPublication(idPublication);
		pub.setDate(currentSqlDate);
		pub.setUserr(user);
		pub.setContents(publication.getContents());
	
		publicationrepo.save(pub);
	
	}


}
