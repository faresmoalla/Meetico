package tn.esprit.meetico.service;

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
import tn.esprit.meetico.entity.Picture;
import tn.esprit.meetico.entity.Reclamation;
import tn.esprit.meetico.entity.User;
import tn.esprit.meetico.entity.reclamationPriority;
import tn.esprit.meetico.entity.reclamationType;
import tn.esprit.meetico.repository.PictureRepository;
import tn.esprit.meetico.repository.UserRepository;
import tn.esprit.meetico.repository.ReclamationRepository;

@Service
@CrossOrigin
public class ReclamationServiceImpl implements IReclamationService {

	@Autowired
	ReclamationRepository reclamationRepository;

	@Autowired
	UserRepository userrepository;

	@Autowired
	CloudinaryService cloudinartservice;
	@Autowired
	PictureRepository picturerepository;

	@Override
	public Reclamation addAffectReclamationUser(Reclamation reclamation, User user, Integer idPicture) {

		//User user = userrepository.findById(userId).orElse(null);
		Picture P =null;
		if(idPicture!= null) {
				P=picturerepository.findById(idPicture).orElse(null);
		}
		reclamation.setUser(user);
		reclamation.setPicture(P);
		Date currentSqlDate = new Date(System.currentTimeMillis());
		reclamation.setSendingDate(currentSqlDate);
		reclamation.setLastModificationDate(currentSqlDate);
		reclamation.setStatus(false);
		
		return reclamationRepository.save(reclamation);
	}

	@Override
	public Reclamation retrieveReclamation(Integer idReclamation) {

		return reclamationRepository.findById(idReclamation).orElse(null);
	}

	@Override
	public void updateReclamation(Reclamation reclamation/*,User user*/) throws ParseException {
	
		Date currentSqlDate = new Date(System.currentTimeMillis());
		Reclamation R = retrieveReclamation(reclamation.getIdReclamation());
		/*if(R.getUser().getUserId()==user.getUserId()) {*/
		R.setDescription(reclamation.getDescription());
		//R.setLastModificationDate(reclamation.getLastModificationDate());
		R.setPriority(reclamation.getPriority());
		R.setType(reclamation.getType());
		R.setLastModificationDate(currentSqlDate);
		//R.setPicture(reclamation.getPicture());
		
		reclamationRepository.save(R);
		//}	
	}

	public Long string2Date(String date) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
		return simpleDateFormat.parse(date).getTime();
	}

	@Override
	public void deleteReclamation(Integer idReclamation/*, User user */) {
		//Reclamation R = retrieveReclamation(idReclamation);
		//if(R.getUser().getUserId()==user.getUserId()) {
		reclamationRepository.deleteById(idReclamation);
		//}
	}

	@Override
	public List<Reclamation> ListAllReclamationsAdmin() {

		return reclamationRepository.findAll();
	}

	@Override
	public Set<Reclamation> listReclamationByPriorityAdmin(reclamationPriority pr) {

		return reclamationRepository.getReclamationByPriority(pr);
	}

	@Override
	public Set<Reclamation> listReclamationByTypeAdmin(reclamationType rt) {

		return reclamationRepository.getReclamationByType(rt);
	}

	@Override
	public Set<Reclamation> listReclamationByPriorityAndTypeAdmin(reclamationPriority pr, reclamationType rt) {

		return reclamationRepository.getReclamationByPriorityAndType(pr, rt);
	}

	@Override
	public Set<Reclamation> ListReclamationByStatusClient(Long userId) {

		return reclamationRepository.getAllReclamationsClientByStatus(userId);

	}

	@Override
	public boolean verif(Integer idReclamation) {
		Reclamation r = retrieveReclamation(idReclamation);
		if (r.getStatus() == false) {
			r.setStatus(true);
		}
		reclamationRepository.save(r);
		return r.getStatus();
	}

	@Override
	public List<Reclamation> ListAllReclamationsClient(Long userId) {

		return reclamationRepository.getAllReclamationsClient(userId);
	}

	@Override

	public float statWatingReclamationByPriorityAndType(reclamationType type, reclamationPriority priority) {
		Integer n = 0, tn;
		float P;

		n = reclamationRepository.nbrWaitingReclamationByPriorityAndType(priority, type);
		tn = (int) reclamationRepository.count();

		P = (n * 100) / tn;

		return P;
	}

	public float statWatingReclamation() {
		Integer n = 0, tn;
		float P;
		n = reclamationRepository.nbrWaitingReclamation();
		tn = (int) reclamationRepository.count();

		P = (n * 100) / tn;

		return P;
	}

	public float statWatingReclamationByType(reclamationType type) {
		Integer n = 0, tn;
		float P;
		n = reclamationRepository.nbrWaitingReclamationByType(type);
		tn = (int) reclamationRepository.count();

		P = (n * 100) / tn;

		return P;

	}

	public float statWatingReclamationByType(reclamationPriority priority) {
		Integer n = 0, tn;
		float P;
		n = reclamationRepository.nbrWaitingReclamationByPriority(priority);
		tn = (int) reclamationRepository.count();
		P = (n * 100) / tn;
		return P;
	}

	@Override
	public void answerAdmin(Reclamation reclamation, User user) throws ParseException {
		Date currentSqlDate = new Date(System.currentTimeMillis());
		Reclamation R = retrieveReclamation(reclamation.getIdReclamation());
		
		if(R.getUser().getUserId()== user.getUserId()) {
		R.setAnswerAdmin(reclamation.getAnswerAdmin());
		R.setAnswerDate(currentSqlDate);
		reclamationRepository.save(R);
		verif(reclamation.getIdReclamation());
		}
		
		
	}

	/*public void answerReclamation(String answer, Integer idReclamation) {
		Reclamation r = retrieveReclamation(idReclamation);
		r.setAnswerAdmin(answer);
		// r.setAnswerDate();
		if (r.getStatus() == false) {
			r.setStatus(true);
		}
		reclamationRepository.save(r);
	}*/

	@Override
	public float statReclamationsTreterNonTreter() {
		Integer f = reclamationRepository.countAllReclamationFalse();
		Integer r = (int) reclamationRepository.count();
		float total = (f * r) / 100;
		return total;
	}

}
