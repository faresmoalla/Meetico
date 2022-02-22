package tn.esprit.service;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.entities.Trip;
import tn.esprit.entities.Utilisateur;
import tn.esprit.repository.TripRepository;
import tn.esprit.repository.UtilisateurRepository;


@Service
@Slf4j
public class TripServiceUmpl implements ITripService{
	@Autowired
	UtilisateurRepository userRepo;
	@Autowired
	TripRepository tripRepo;
	

	@Override
	public void addTrip(Trip trip, List<Long> idUsers) {
		
		for(Long iduser :idUsers) {
			Utilisateur u =userRepo.findById(iduser).orElse(null);
			u.getTrips().add(trip);
			userRepo.save(u);
		}
	}

	@Override
	public void updateTrip(Trip trip, Integer idTrip) {
		Trip t = tripRepo.findById(idTrip).orElse(null);
		t.setDestination(trip.getDestination());
		t.setEndDate(trip.getEndDate());
		t.setObject(trip.getObject());
		t.setStartDate(trip.getStartDate());
		t.setUtilisateur(t.getUtilisateur());
		tripRepo.save(t);
	
	}

	@Override
	public void deleteTrip(Integer idTrip) {
	
		tripRepo.deleteById(idTrip);
		
	}

	@Override
	public Trip affichDetailTrip(Integer idTrip) {
		Locale locale = LocaleContextHolder.getLocale();
		
		return tripRepo.findById(idTrip).orElse(null);
	}

	@Override
	public List<Trip> affichTrip() {
		
		return tripRepo.findAll();
	}

	@Override
	public void ajouttrip(Trip trip) {
		tripRepo.save(trip);
		
	}

	@Override
	public void affecterlisteutilisateurautrip(List<Long> idutilisateurs,Integer idtrip) {
		Trip t =tripRepo.findById(idtrip).orElse(null);
		for (Long idutilisateur : idutilisateurs) {
			Utilisateur u = userRepo.findById(idutilisateur).orElse(null);
			u.getTrips().add(t);
			userRepo.save(u);
		}
		
	}

	@Override
	public List<Utilisateur> afficherutilisateurbymatching(String destination,Date startdate) {
		// TODO Auto-generated method stub
		return tripRepo.matchingutilisateur(destination,startdate);
	}

	

}
