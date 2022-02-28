package tn.esprit.service;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.entity.Trip;
import tn.esprit.entity.User;
import tn.esprit.repository.TripRepository;
import tn.esprit.repository.UserRepository;




@Service
@Slf4j
public class TripServiceUmpl implements ITripService{
	@Autowired
	UserRepository userRepo;
	@Autowired
	TripRepository tripRepo;
	

	@Override
	public void addTrip(Trip trip, List<Long> idUsers,Long idEnt) {
		Trip t = ajouttrip(trip, idEnt);					
		for(Long iduser :idUsers) {
			User u =userRepo.findById(iduser).orElse(null);
			u.getTrips().add(t);
			userRepo.save(u);
		}
	}

	@Override
	public void updateTrip(Trip trip, Integer idTrip) {
		Trip t = tripRepo.findById(idTrip).orElse(null);
		t.setDestination(trip.getDestination().toUpperCase());
		t.setEndDate(trip.getEndDate());
		t.setObject(trip.getObject());
		t.setStartDate(trip.getStartDate());
		t.setUser(t.getUser());
		t.setUsers(t.getUsers());
		tripRepo.save(t);
	
	}

	@Override
	public void deleteTrip(Integer idTrip) {
	
		tripRepo.deleteById(idTrip);
		
	}

	@Override
	public Trip affichDetailTrip(Integer idTrip) {
		
		
		return tripRepo.findById(idTrip).orElse(null);
	}

	@Override
	public List<Trip> affichTrip() {
		
		return tripRepo.findAll();
	}

	@Override
	public Trip ajouttrip(Trip trip,Long idUSer) {
		String d=trip.getDestination();
		String dm=d.toUpperCase();
		trip.setDestination(dm);
		User u = userRepo.findById(idUSer).orElse(null);
		trip.setUser(u);
		return tripRepo.save(trip);		
	}

	@Override
	public void affecterlisteutilisateurautrip(List<Long> idutilisateurs,Integer idtrip) {
		Trip t =tripRepo.findById(idtrip).orElse(null);
		for (Long idutilisateur : idutilisateurs) {
			User u = userRepo.findById(idutilisateur).orElse(null);
			u.getTrips().add(t);
			userRepo.save(u);
		}
		
	}

	@Override
	public List<User> afficherutilisateurbymatching(String destination,Date startdate,String city) {
		
		return tripRepo.matchingutilisateur(destination,startdate,city);
	}

	@Override
	public void deleteutilisateurdetrip(Integer idtrip, List<Long> idusers) {
		Trip t = tripRepo.findById(idtrip).orElse(null);
		//Set <User> u =  t.getUsers();
		for(Long iduser :idusers) {
			User user =userRepo.findById(iduser).orElse(null);
			user.getTrips().remove(t);
			userRepo.save(user);
		}
		
		//tripRepo.deleteuserfromtrip(idtrip, idusers);
		/*
		Trip t = tripRepo.findById(idtrip).orElse(null);
		Set <User> u =  t.getUsers();
		for(Long iduser :idusers) {
			User user =userRepo.findById(iduser).orElse(null);
			u.remove(user);
			}
		for (User us : u) {
			log.info("user name:"+us.getFirstName());
		}
		
		//tripRepo.flush();
		
	t.setUsers(u);
	tripRepo.save(t);
	}
		
		
		Trip t = tripRepo.findById(idtrip).orElse(null);
		for(Long iduser :idusers) {
			Utilisateur u =userRepo.findById(iduser).orElse(null);
			t.getUtilisateur().remove(u);
			
		}
		tripRepo.save(t);*/
		}
		
	

	

	

}
