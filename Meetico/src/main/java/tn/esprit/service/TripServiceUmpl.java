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
	public void updateTrip(Trip trip, Long idUser, Integer idTrip) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteTrip(Integer idTrip) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Trip affichDetailTrip(Integer idTrip) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Trip> affichTrip() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
