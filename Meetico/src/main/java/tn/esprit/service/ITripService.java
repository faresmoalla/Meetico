package tn.esprit.service;

import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import tn.esprit.entity.Trip;
import tn.esprit.entity.User;



public interface ITripService {
	public Trip ajouttrip(Trip trip,Long idUser);
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
	public List<String>  nbrUserPourChaqueVoyage();

}
