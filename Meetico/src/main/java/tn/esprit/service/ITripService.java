package tn.esprit.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.Document;

import tn.esprit.entity.Trip;
import tn.esprit.entity.User;



public interface ITripService {
	public Trip ajouttrip(Trip trip,Long idUser);
	public void exporttripToPdf(HttpServletResponse response,Integer idtrip);
	public void affecterlisteutilisateurautrip(List<Long> idutilisateurs,Integer idtrip);
	public void addTrip(Trip trip,List<Long> idUser, Long idEnt);
	public void updateTrip(Trip trip,Integer idTrip);
	public void deleteTrip(Integer idTrip);
	public Trip affichDetailTrip(Integer idTrip);
	public List<Trip> affichTrip();
	public List<User> afficherutilisateurbymatching(String destination,Date startdate,String city);
	public void deleteutilisateurdetrip(Integer idtrip,List<Long> iduser);
	public void affecterFileToTip(List<Long> idFiles,Integer idTrip);
	public int listUserByVoyage(Integer idTrip);
	public List<String> nbrUserPourChaqueVoyage();
	public String meilleurDestination();
	public List<String> nbrDeVoyagePourChaqueUser();
	public List<String> nbrdevisitepourchaquedestination();
	

}
