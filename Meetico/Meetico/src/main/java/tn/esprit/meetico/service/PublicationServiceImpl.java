package tn.esprit.meetico.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import tn.esprit.meetico.entity.Alert;
import tn.esprit.meetico.entity.DictionnaireBadWords;
import tn.esprit.meetico.entity.PostDislike;
import tn.esprit.meetico.entity.PostLike;
import tn.esprit.meetico.entity.Publication;
import tn.esprit.meetico.entity.User;
import tn.esprit.meetico.repository.AlertRepository;
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
	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job job;

/////////////////////////Ajout Publication//////////////////
	@Override
	public void addPublication(Publication publication, User user) {
		//User user = utiRepo.findById(idUser).orElse(null);
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
		
		if(publication.getContents().contains("@all")) {
			JobParameters params = new JobParametersBuilder().addString("JobID", String.valueOf(System.currentTimeMillis()))
					.toJobParameters();
			
				try {
					jobLauncher.run(job, params);
				} catch (JobExecutionAlreadyRunningException e) {
					// TODO Auto-generated catch block
					
				} catch (JobRestartException e) {
					// TODO Auto-generated catch block
					
				} catch (JobInstanceAlreadyCompleteException e) {
					
				} catch (JobParametersInvalidException e) {
				
				}
			
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
	public void addLike(Long idPublicaiton,User user) {
		PostLike lk = new PostLike();
		Publication publication = publicationrepo.findById(idPublicaiton).orElse(null);
		//User user = utiRepo.findById(idUser).orElse(null);
		PostLike like = likeRepo.GetLike(idPublicaiton, user.getUserId());
		PostDislike dislike = dislikeRepo.GetDislike(idPublicaiton,user.getUserId());
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
	public void addDislike(Long idPublicaiton, User user) {
		PostDislike lk = new PostDislike();
		Publication publication = publicationrepo.findById(idPublicaiton).orElse(null);
		//User user = utiRepo.findById(idUser).orElse(null);
		PostLike like = likeRepo.GetLike(idPublicaiton, user.getUserId());
		PostDislike dislike = dislikeRepo.GetDislike(idPublicaiton, user.getUserId());
		lk.setPublication(publication);
		lk.setUtilis(user);
		if (like == null && dislike == null) {
			dislikeRepo.save(lk);
		}
		else if (dislike == null && like != null) {
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
