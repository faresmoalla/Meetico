package tn.esprit.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.mail.SendFailedException;

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

import lombok.extern.slf4j.Slf4j;
import tn.esprit.entity.Gender;
import tn.esprit.entity.Trip;
import tn.esprit.entity.User;
import tn.esprit.repository.TripRepository;
import tn.esprit.repository.UserRepository;
import tn.esprit.service.EmailServiceImpl;
import tn.esprit.service.ITripService;
import tn.esprit.service.TripServiceUmpl;
@Slf4j
@RestController
@RequestMapping("/Trip")
public class TripController {
	@Autowired
	ITripService tripService;
	@Autowired
	EmailServiceImpl emailsend;
	@Autowired
	TripRepository tripRepo;
	@Autowired
	UserRepository userRepo;
	
	@PostMapping("/add-trip-affecterutilisateur/{id-user}/{idEnt}")
	@ResponseBody
	public void addTripetaffecterutilisateur(@RequestBody Trip t, @PathVariable("id-user") List<Long> iduser, @PathVariable("idEnt") Long idEnt) throws SendFailedException{
		tripService.addTrip(t, iduser,idEnt);
		User entrepreneur =t.getUser();
		List <User> ustrip =(List<User>) userRepo.findAllById(iduser);
		List<User> users =tripService.afficherutilisateurbymatching(t.getDestination(), t.getStartDate(), t.getUser().getCity());
		int day =t.getStartDate().getDate();
		int month =t.getStartDate().getMonth()+1;
		int year =t.getStartDate().getYear()+1900;
		if(users.size() !=0) {
			for(User us :users) {
				for(User u: users) {
					emailsend.sendEmail(u.getEmail(), "JOIN us ", (u.getGender().equals(Gender.MALE) ? "Welcome Mr. " : "Welcome Ms. ")
							+ u.getFirstName() + ", \n we have a trip to "+t.getDestination()+" with our employee "+(us.getGender().equals(Gender.MALE) ? " Mr. " : " Ms. ")+us.getFirstName()+" "+us.getLastName()
							+"  "+"from the city "+u.getCity()+"at the date "+day+"/"+month+"/"+year+ "  "+entrepreneur.getUsername().substring(0, 1).toUpperCase() + entrepreneur.getUsername().substring(1) + " Group. \nThe Meetico Team.");
			}
		
			}
		
		}else {
			log.info("we have no match");
		}
	}
	@PutMapping("/affecter-utilisateur/{id-trip}/{user}")
	@ResponseBody
	public void affecterutilisateur(@PathVariable("user") List<Long> user,@PathVariable("id-trip") Integer idtrip) throws SendFailedException{
		tripService.affecterlisteutilisateurautrip(user, idtrip);
		Trip t =tripRepo.findById(idtrip).orElse(null);
		User entrepreneur =t.getUser();
		Set <User> ustrip =t.getUsers();
		List<User> users =tripService.afficherutilisateurbymatching(t.getDestination(), t.getStartDate(), t.getUser().getCity());
		int day =t.getStartDate().getDate();
		int month =t.getStartDate().getMonth()+1;
		int year =t.getStartDate().getYear()+1900;
		if(users.size()!=0) {
			for(User us :users) {
				for(User u: users) {
					emailsend.sendEmail(u.getEmail(), "JOIN us ", (u.getGender().equals(Gender.MALE) ? "Welcome Mr. " : "Welcome Ms. ")
							+ u.getFirstName() + ", \n we have a trip to "+t.getDestination()+" with our employee "+(us.getGender().equals(Gender.MALE) ? " Mr. " : " Ms. ")+us.getFirstName()+" "+us.getLastName()
							+"  "+"from the city "+u.getCity()+"at the date "+day+"/"+month+"/"+year+ " "+entrepreneur.getUsername().substring(0, 1).toUpperCase() + entrepreneur.getUsername().substring(1) + " Group. \nThe Meetico Team.");
				}
			}
		}else {
			log.info("we have no match");
		}
		
		
	}
		
	
	@PostMapping("/ajouttrip/{id-ent}")
	@ResponseBody
	public Trip ajouttrip( @RequestBody Trip t,@PathVariable("id-ent") Long idEnt) throws SendFailedException{
		
		return tripService.ajouttrip(t,idEnt);
		
		
	}
	
	@PutMapping("/update-trip/{id-trip}")
	@ResponseBody
	public void updatetrip(@RequestBody Trip t, @PathVariable("id-trip") Integer idtrip){
		tripService.updateTrip(t, idtrip);
		
	}
	@DeleteMapping("/delete-trip/{id-trip}")
	@ResponseBody
	public void deletetrip( @PathVariable("id-trip") Integer idtrip){
		Trip t=tripRepo.findById(idtrip).orElse(null);
		Set<User> user=t.getUsers();
		List<Long> id = new ArrayList<>() ;
		for(User u:user) {
			
			id.add(u.getUserId());
			
		}
		tripService.deleteutilisateurdetrip(idtrip, id);
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
	@GetMapping("/get-utilisateurbymatching/{destination}/{startdate}/{city}")
	public List<User> afficherutilisateurbymatching(@PathVariable("destination") String destination,@PathVariable("startdate") java.sql.Date startdate,@PathVariable("city") String city) {
		
	
		return tripService.afficherutilisateurbymatching(destination.toUpperCase(),startdate,city.toUpperCase());
	}
	@PutMapping("/delete-userfromtrip/{id-trip}/{userid}")
	@ResponseBody
	public void deleteutilisateurfromtrip( @PathVariable("id-trip") Integer idtrip,@PathVariable("userid") List<Long> idusers){
		
		tripService.deleteutilisateurdetrip(idtrip, idusers);
		
	}
}
