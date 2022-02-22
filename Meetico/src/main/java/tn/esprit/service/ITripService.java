package tn.esprit.service;

import java.util.Date;
import java.util.List;

import tn.esprit.entities.Trip;
import tn.esprit.entities.Utilisateur;

public interface ITripService {
	public void ajouttrip(Trip trip);
	public void affecterlisteutilisateurautrip(List<Long> idutilisateurs,Integer idtrip);
	public void addTrip(Trip trip,List<Long> idUser);
	public void updateTrip(Trip trip,Integer idTrip);
	public void deleteTrip(Integer idTrip);
	public Trip affichDetailTrip(Integer idTrip);
	public List<Trip> affichTrip();
	public List<Utilisateur> afficherutilisateurbymatching(String destination,Date startdate);

}
