package tn.esprit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.entities.Trip;
import tn.esprit.entities.Utilisateur;
import tn.esprit.repository.TripRepository;
import tn.esprit.repository.UtilisateurRepository;
@Service
public class TripServiceUmpl implements ITripService{
	@Autowired
	UtilisateurRepository userRepo;
	@Autowired
	TripRepository tripRepo;
	

	@Override
	public void addTrip(Trip trip, Long idUser) {
		Utilisateur u=  userRepo.findById(idUser).orElse(null);
		u.getTrips().add(trip);
		userRepo.save(u);
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
		// TODO Auto-generated method stub
		tripRepo.deleteById(idTrip);
		
	}

	@Override
	public Trip affichDetailTrip(Integer idTrip) {
		// TODO Auto-generated method stub
		return tripRepo.findById(idTrip).orElse(null);
	}

	@Override
	public List<Trip> affichTrip() {
		// TODO Auto-generated method stub
		return tripRepo.findAll();
	}

	

}
