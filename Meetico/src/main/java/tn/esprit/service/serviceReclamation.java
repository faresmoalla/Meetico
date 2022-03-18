package tn.esprit.service;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import java.util.List;
import java.util.Set;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;


import lombok.extern.slf4j.Slf4j;
import tn.esprit.entity.Picture;
import tn.esprit.entity.Reclamation;
import tn.esprit.entity.User;
import tn.esprit.entity.reclamationPriority;
import tn.esprit.entity.reclamationType;
import tn.esprit.repository.PictureRepository;
import tn.esprit.repository.UserRepository;
import tn.esprit.repository.reclamationRepository;







@Service
@Slf4j
@CrossOrigin
public class serviceReclamation implements Ireclamation {
	@Autowired
	reclamationRepository reclamationrepository;
	@Autowired
	UserRepository userrepository;

	@Autowired
	CloudinaryService cloudinartservice; 
	@Autowired
	PictureRepository  picturerepository;


	@Override
	public Reclamation addAffectReclamationUser(Reclamation reclamation, Long userId,Integer idPicture){

		User user = userrepository.findById(userId).orElse(null);
		Picture P= picturerepository.findById(idPicture).orElse(null);
		reclamation.setUser(user);
		reclamation.setPicture(P);
		return reclamationrepository.save(reclamation);
	}


	
	@Override
	public Reclamation retrieveReclamation(Integer idReclamation) {
		
		return reclamationrepository.findById(idReclamation).orElse(null);
	}
	@Override
	public void updateReclamation(Reclamation reclamation) throws ParseException {
		
		
		
		
		Reclamation R = retrieveReclamation(reclamation.getIdReclamation());
		R.setDescription(reclamation.getDescription());
		R.setLastModificationDate(reclamation.getLastModificationDate());
		R.setPriority(reclamation.getPriority());
		R.setType(reclamation.getType());

		R.setLastModificationDate(new Date(string2Date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss")))));
		R.setPicture(reclamation.getPicture());

		reclamationrepository.save(R);	
	}
	
	public Long string2Date(String date) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
		return simpleDateFormat.parse(date).getTime();
	}

	@Override
	public void deleteReclamation(Integer idReclamation) {
		
		reclamationrepository.deleteById(idReclamation);
		
	}


	@Override
	public List<Reclamation> ListAllReclamationsAdmin() {
		
		return reclamationrepository.findAll();
	}


	@Override
	public Set<Reclamation> listReclamationByPriorityAdmin(reclamationPriority pr) {
		
		return reclamationrepository.getReclamationByPriority(pr);
	}


	@Override
	public Set<Reclamation> listReclamationByTypeAdmin(reclamationType rt) {
		
		return reclamationrepository.getReclamationByType(rt);
	}


	@Override
	public Set<Reclamation> listReclamationByPriorityAndTypeAdmin(reclamationPriority pr, reclamationType rt) {
		
		return reclamationrepository.getReclamationByPriorityAndType(pr, rt);
	}


	@Override
	public Set<Reclamation> ListReclamationByStatusClient(Long userId) {
		
		return reclamationrepository.getAllReclamationsClientByStatus(userId);
		
	}


	
	
	
	@Override
	public boolean verif(Integer idReclamation) {
		Reclamation f=retrieveReclamation( idReclamation);
		if(f.getStatus() == false) {
		f.setStatus(true);
		}
		reclamationrepository.save(f);
		return f.getStatus();
	}

	
	
	
	@Override
	public List<Reclamation> ListAllReclamationsClient(Long userId) {
		
		return reclamationrepository.getAllReclamationsClient(userId);
	}

	@Override

	public float statWatingReclamationByPriorityAndType(reclamationType type ,reclamationPriority priority) {
		Integer n=0 ,tn;
		float P;
		
			n=reclamationrepository.nbrWaitingReclamationByPriorityAndType(priority, type);
		tn=(int) reclamationrepository.count();
		
		P=(n*100)/tn;
		
		return P;
	}
	
	public float statWatingReclamation() {
		Integer n=0 ,tn;
		float P;
		 n = reclamationrepository.nbrWaitingReclamation();
		 tn=(int) reclamationrepository.count();
			
			P=(n*100)/tn;
			
			return P;
	}
	
	public float statWatingReclamationByType(reclamationType type ) 
	{Integer n=0 ,tn;
	float P;
	 n = reclamationrepository.nbrWaitingReclamationByType(type);
	 tn=(int) reclamationrepository.count();
		
		P=(n*100)/tn;
		
		return P;
		
	}

	public float statWatingReclamationByType(reclamationPriority priority ) 
	{Integer n=0 ,tn;
	float P;
	 n = reclamationrepository.nbrWaitingReclamationByPriority(priority);
	 tn=(int) reclamationrepository.count();
		P=(n*100)/tn;
		return P;
	}

	@Override
	public void answerAdmin(Reclamation reclamation) throws ParseException {
		

		Reclamation R = retrieveReclamation(reclamation.getIdReclamation());
		R.setAnswerAdmin(reclamation.getAnswerAdmin());
		R.setAnswerDate(new Date(string2Date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss")))));
		reclamationrepository.save(R);	
		verif(reclamation.getIdReclamation());
	}
	

	public void answerReclamation(String answer, Integer idReclamation) {
		Reclamation r=retrieveReclamation(idReclamation);
		r.setAnswerAdmin(answer);
		//r.setAnswerDate();
		if(r.getStatus() == false) {
			r.setStatus(true);
			}
			reclamationrepository.save(r);
	}




	@Override
	public float statReclamationsTreterNonTreter() {
		Integer f= reclamationrepository.countAllReclamationFalse();
		Integer r=(int) reclamationrepository.count();
			float	total=(f*r)/100;
		return total;
	}
	
	
	
	
	

	   

	 
	    

	

	
	
	
	

}
