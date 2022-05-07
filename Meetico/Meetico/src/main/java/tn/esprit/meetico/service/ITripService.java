package tn.esprit.meetico.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import tn.esprit.meetico.entity.StatMeilleurDesitnation;
import tn.esprit.meetico.entity.Trip;
import tn.esprit.meetico.entity.User;
import tn.esprit.meetico.entity.DestionationVisitorsCount;

public interface ITripService {
	
	public Trip ajouttrip(Trip trip, Long idUser);

	//public void exporttripToPdf(HttpServletResponse response, Integer idtrip);

	public void affecterlisteutilisateurautrip(List<Long> idutilisateurs, Integer idtrip);

	public void addTrip(Trip trip, List<Long> idUser, Long idEnt);

	public void updateTrip(Trip trip, Integer idTrip);

	public void deleteTrip(Integer idTrip);

	public Trip affichDetailTrip(Integer idTrip);

	public List<Trip> affichTrip();

	public List<User> afficherutilisateurbymatching(String destination, Date startdate, String city);
	public List<Trip> searchbydestination(String destination);

	public void deleteutilisateurdetrip(Integer idtrip, List<Long> iduser);

	public void affecterFileToTip(Long idFiles, Integer idTrip);
	
	public Set<Trip> affichetListVoyageByEntrepreneur(Long idEnt);

	public int listUserByVoyage(Integer idTrip);

	public List<String> nbrUserPourChaqueVoyage();

	public String favoriteDestination();

	public List<String> userDestionationsVisitsCount();

	public List<String> destionationVisitorsCount();
	
	public List<DestionationVisitorsCount> destionationVisitorsCountA();
	
	public String meilleurDestination();
	
	public List<StatMeilleurDesitnation> listmeilleurdestination();
}
