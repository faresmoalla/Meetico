package tn.esprit.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.entities.Trip;
import tn.esprit.entities.Utilisateur;
import tn.esprit.service.ITripService;

@RestController
@RequestMapping("/Trip")
public class TripController {
	@Autowired
	ITripService tripService;
	
	@PostMapping("/add-trip-affecterutilisateur/{id-user}")
	@ResponseBody
	public void addTripetaffecterutilisateur(@RequestBody Trip t, @PathVariable("id-user") List<Long> iduser){
		tripService.addTrip(t, iduser);
		
	}
	@PutMapping("/affecter-utilisateur/{id-trip}/{user}")
	@ResponseBody
	public void affecterutilisateur(@PathVariable("user") List<Long> user,@PathVariable("id-trip") Integer idtrip){
		tripService.affecterlisteutilisateurautrip(user, idtrip);
		
	}
	@PostMapping("/ajouttrip")
	@ResponseBody
	public void ajouttrip( @RequestBody Trip t){
		tripService.ajouttrip(t);
		
	}
	
	@PutMapping("/update-trip/{id-trip}")
	@ResponseBody
	public void updatetrip(@RequestBody Trip t, @PathVariable("id-trip") Integer idtrip){
		tripService.updateTrip(t, idtrip);
		
	}
	@DeleteMapping("/delete-trip/{id-trip}")
	@ResponseBody
	public void deletetrip( @PathVariable("id-trip") Integer idtrip){
		tripService.deleteTrip(idtrip);
		
	}
	@GetMapping("/get-trip/{id-trip}")
	@ResponseBody
	public Trip gettripbyid( @PathVariable("id-trip") Integer idtrip){
		return tripService.affichDetailTrip(idtrip);
		
	}
	@GetMapping("/get-trip")
	@ResponseBody
	public List<Trip> gettrips( ){
		return tripService.affichTrip();
		
	}
	@GetMapping("/get-utilisateurbymatching/{destination}/{startdate}")
	public List<Utilisateur> afficherutilisateurbymatching(@PathVariable("destination") String destination,@PathVariable("startdate") java.sql.Date startdate){
		return tripService.afficherutilisateurbymatching(destination,startdate);
	}
}
