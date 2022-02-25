package tn.esprit.service;

import java.util.Date;
import java.util.List;

import tn.esprit.entity.Trip;
import tn.esprit.entity.User;



public interface ITripService {
	public void ajouttrip(Trip trip);
	public void affecterlisteutilisateurautrip(List<Long> idutilisateurs,Integer idtrip);
	public void addTrip(Trip trip,List<Long> idUser);
	public void updateTrip(Trip trip,Integer idTrip);
	public void deleteTrip(Integer idTrip);
	public Trip affichDetailTrip(Integer idTrip);
	public List<Trip> affichTrip();
	public List<User> afficherutilisateurbymatching(String destination,Date startdate);
	public void deleteutilisateurdetrip(Integer idtrip,List<Long> iduser);

}
